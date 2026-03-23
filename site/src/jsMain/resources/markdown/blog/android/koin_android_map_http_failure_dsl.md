---
layout: .components.layouts.BlogLayout
title: "Kotlin DSL을 활용한 에러 핸들링 설계"
date: "2026-03-23"
description: ""
tags: ["android", "kotlin", "koin"]
---

# Kotlin DSL을 활용한 에러 핸들링 설계

과거 코인에서는 `ErrorHandler`를 이용해 에러를 처리하였다.

다만 현재 팀원 중에는 그 구조를 직접 사용해 본 사람이 없고, 따라 Result 기반으로 에러를 명시적으로 다루는 방향으로 정리되었다.

## Result.failure 분기처리

처음에는 아래와 같이 코드를 작성하였다.

```kotlin
return runCatching {
    remoteCall()
}.onFailure { exception ->
    return Result.failure(
        when (exception) {
            is HttpException -> {
                when (exception.code()) {
                    401 -> UnauthorizedException()
                    404 -> NotFoundException()
                    else -> exception.getErrorResponse().toKoinUnknownErrorException()
                }
            }

            else -> exception
        }
    )
}
```

이 방식은 단순하고, 처음 보기에도 동작이 명확하다.

실패가 발생하면 `HttpException`인지 확인하고, 상태 코드별로 `when` 분기한 뒤, 도메인 예외로 바꿔서 `Result.failure(...)`로 다시 감싸면 된다.

문제는 이 코드가 Repository마다 거의 비슷한 형태로 계속 반복된다는 점이었다.

어떤 함수는 `401`, `404`만 처리하고, 어떤 함수는 `400`, `403`, `404`를 처리하고, 어떤 함수는 `getErrorResponse().code`까지 한 번 더 확인해야 했다.

하지만 전체적인 뼈대는 거의 같았다.

즉, 실패 처리의 정책보다 보일러플레이트가 더 많이 보이기 시작했다.

## recoverCatching

그러다 보니 자연스럽게 `recoverCatching`을 떠올리게 되었다.

`runCatching`으로 감싼 뒤, 실패를 복구하는 흐름에 맞춰 아래처럼 작성할 수 있기 때문이다.

```kotlin
return runCatching {
    remoteCall()
}.recoverCatching { exception ->
    when (exception) {
        is HttpException -> {
            when (exception.code()) {
                401 -> throw UnauthorizedException()
                404 -> throw NotFoundException()
                else -> throw exception.getErrorResponse().toKoinUnknownErrorException()
            }
        }

        else -> throw exception
    }
}
```

이 방식은 `Result.failure(...)`를 직접 반환하는 방식보다 조금 더 함수형에 가깝고, "실패를 다른 실패로 변환한다"는 의도도 잘 드러난다.

다만 실제로 써보면 생각보다 큰 차이는 없었다.

결국 안에서 `when`을 돌리고, 예외를 다시 던지고, HTTP 상태 코드별 분기 로직을 매번 반복해야 한다는 점은 그대로였기 때문이다.

즉, `recoverCatching`은 표현 방식의 차이는 있었지만, 중복 자체를 줄여주지는 못했다.

## named parameter 방식의 mapHttpFailure

이 문제를 해결하기 위해, 처음 만든 `mapHttpFailure`는 아래와 같은 named parameter 방식이었다.

```kotlin
runCatching {
    remoteCall()
}.mapHttpFailure(
    e400 = BadRequestException(),
    e401 = UnauthorizedException(),
    e404 = NotFoundException()
)
```

이 구조의 장점은 명확했다.

- 반복되던 `HttpException` 체크가 감춰진다
- Repository에서는 필요한 상태 코드만 선언하면 된다
- 읽을 때도 "이 API는 어떤 예외를 처리하는지"가 바로 보인다

실제로 HTTP 상태 코드 하나가 예외 하나에 대응되는 경우에는 이 구조가 꽤 잘 맞았다.

다만, 이 구조에는 명확한 한계가 있었다.

## named parameter 방식의 한계

문제는 코인에서 애초에 HTTP 상태 코드만으로는 부족하고, 서버의 커스텀 에러 코드까지 같이 봐야 하는 경우가 있었다는 점이었다.

예를 들어 같은 `400` 응답이어도 실제 의미는 여러 가지일 수 있었다.

- 필수 옵션 그룹을 선택하지 않은 경우
- 선택 가능한 수를 초과한 경우
- 그룹에 속하지 않는 옵션을 선택한 경우

즉, `400 -> BadRequestException()`처럼 단순하게 적기에는 도메인 의미가 너무 많이 섞여 있었다.

결국 상태 코드와 함께, 서버에서 내려주는 커스텀 에러 코드를 같이 사용해서 분기처리를 해야 했다.

결국 named parameter 방식의 `mapHttpFailure` 함수는 하나의 상태 코드에 하나의 예외를 매핑하는 데는 좋지만, 같은 `400` 안에서 다시 에러 코드를 보고 분기하려면 결국 함수 바깥에서 `when`을 쓰거나, 함수 내부를 더 복잡하게 만들어야 했다.

## Kotlin DSL 방식의 mapHttpFailure

이 문제를 해결하기 위해 떠올린 것이 Kotlin DSL이었다.

DSL은 Domain Specific Languages의 약자로, 높은 가독성을 제공한다.

이를 활용해, 아래와 같은 구조로 `mapHttpFailure`를 새로 설계했다.

```kotlin
runCatching {
    remoteCall()
}.mapHttpFailure {
    on(400) throws BadRequestException()
    on(401) throws UnauthorizedException()
    on(404) throws NotFoundException()
}
```

기존의 named parameter 방식의 `mapHttpFailure`와 다르게, 아래와 같이 커스텀 에러 코드를 같이 처리할 수 있도록 설계했다.
```kotlin
mapHttpFailure {
    on(400, "REQUIRED_OPTION_GROUP_MISSING") throws RequiredOptionGroupMissingException()
    on(400, "INVALID_OPTION_IN_GROUP") throws InvalidOptionInGroupException()
    on(500..599) throws ServerException()
}
```

이 방식의 장점은 다음과 같다.

- 에러 매핑 로직이 한 블록에 모인다
- 복잡한 `when` 분기가 Repository 곳곳에 퍼지지 않는다
- 상태 코드와 에러 코드를 같은 문법으로 다룰 수 있다
- 범위 기반 매핑도 가능하다

무엇보다 Repository를 읽을 때 "이 API는 어떤 실패를 의도적으로 처리하는지"가 잘 보였다.

## 초기 구현과 수정

처음 올린 PR에서는 내부적으로 상태 코드와 에러 코드를 `Pair`로 묶어서 `mutableMapOf`에 저장한 뒤, 마지막에 이를 조회하는 방식으로 구현했다.

처음에는 이게 꽤 괜찮아 보였다.

선언한 규칙들을 다 모아두고, 마지막에 현재 응답과 일치하는 것을 찾는 구조였기 때문이다.

근데 멘토님께서 해당 PR에 꽤 많은 리뷰를 달아주셨고, 그 중 고민을 다시 하게 만든 리뷰가 있었다.

> 굳이 map으로 할 필요가 있나해서요

리뷰를 보고 생각 해보니, 내가 코드를 너무 어렵게 작성했다는 생각이 들었다.

사실 이 구조에서 `Mapper`가 해야 하는 일은 단순했다.

- 현재 `HttpException`을 들고 있고
- `on(...)` 선언을 순서대로 읽다가
- 조건이 맞으면 예외를 하나 정하고
- 마지막에 그것을 반환하거나, 매핑이 없으면 `KoinUnknownErrorException` 넘기면 된다.

즉, 선언된 규칙을 모두 컬렉션에 저장할 필요가 없었다.

리뷰를 반영한 뒤에는 `mutableMapOf`를 없애고, 현재 응답과 조건이 맞으면 바로 `mappedException`을 갱신하는 방식으로 단순화했다.

결과적으로 코드는 더 짧아졌고, 동작도 오히려 더 직관적으로 바뀌었다.

## ClubRepository 적용

Kotlin DSL 기반 새 DSL을 만든 뒤, 우선 `ClubRepository`에 먼저 적용했다.

기존에는 `Result.failure(...)` 직접 반환 방식에서 비롯된 보일러플레이트가 많이 남아 있었다.

`HttpException`인지 확인하고, 상태 코드별 `when`을 돌리고, 마지막에는 unknown exception으로 넘기는 흐름이 계속 반복되고 있었다.

이걸 DSL로 바꾸고 나니, 각 API의 에러 정책만 남고 나머지 잡음은 많이 사라졌다.

예외 매핑을 선언하는 쪽은 짧아졌고, "무엇을 처리하는지"만 남게 되었다.

## 후속 PR 적용

이후 올린 PR에서는 이 DSL을 다른 Repository들에도 적용했다.

예전에는 하나의 함수 안에서 아래 흐름이 반복되었다.

- `HttpException`인지 확인
- `code()`로 상태 코드 분기
- 필요하면 `getErrorResponse().code`로 한 번 더 분기
- 그 외는 `KoinUnknownErrorException` 처리

DSL로 바꾼 뒤에는 "어떤 조합에서 어떤 예외를 던질지"만 나열하면 되었다.

결과적으로 보일러 플레이트가 감소하여 코드량이 약 78% 감소하였고, 훨씬 더 가독성 높은 코드를 작성할 수 있게 되었다.

## 아쉬운 점

물론 아쉬운 점도 있었다.

만 `HttpException`을 `KoinUnknownErrorException`으로만 넘기고 싶은 경우에는, 현재 문법상 `mapHttpFailure { }`처럼 빈 블록을 써야 하는 상황이 생긴다.

## 마무리

이번 작업은 `HttpException` 매핑 과정에서 발생하는 보일러 플레이트를 줄이고, 가독성을 향상하기 위한 작업이었다.

결과적으로 Kotlin DSL 방식의 코드를 통해 가독성 향상과 보일러 플레이트 감소라는 목표를 달성할 수 있었고, 그 과정에서 받은 리뷰를 통해 mapper의 역할에 대해 다시 한번 고민해볼 수 있었다.

PR 링크:
- [Issue #1280 - Kotlin DSL을 통한 HTTP 에러 매핑 로직 구현](https://github.com/BCSDLab/KOIN_ANDROID/issues/1280)
- [PR #1281 - Kotlin DSL 방식의 mapHttpFailure 추가](https://github.com/BCSDLab/KOIN_ANDROID/pull/1281)
- [PR #1282 - 일부 Repository를 mapHttpException으로 리팩토링](https://github.com/BCSDLab/KOIN_ANDROID/pull/1282)
