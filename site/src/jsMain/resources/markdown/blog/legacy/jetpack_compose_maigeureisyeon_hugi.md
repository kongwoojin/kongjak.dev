---
layout: .components.layouts.BlogLayout
title: "Jetpack Compose 마이그레이션 후기"
date: "2023-07-10"
description: ""
tags: ["compose", "jetpack-compose", "migration", "android"]
---

# Jetpack Compose 마이그레이션 후기

이전에 써보고 아직은 아닌 것 같아 보류했던 Jetpack Compose를 다시 한번 써봤다.

전에는 가볍게 어떤 건지 맛만 본 수준이라면, 이번에는 아예 개발 중이던 토이 프로젝트를 Compose로 완전히 넘어가 보려고 하였고, 생각보다 만족스러웠다.

일단 Jetpack Compose에 대해 간단하게 소개를 해보자면, 구글에서 개발한 안드로이드 선언형 UI 프레임워크로, 레이아웃 구성에 xml을 일절 사용하지 않는다는 특징을 가지고 있다.

기본적으로 @Composable Annotation을 붙인 함수 안에 뷰를 선언하는 형태를 가지고 있다.
자세한 내용은 [구글 공식 문서](https://developer.android.com/jetpack/compose)에 잘 나와있다.

본격적으로 프로젝트를 Compose로 마이그레이션 하기 앞서, 걱정됐던 몇 가지가 있다.

1. 기존의 프로젝트를 Compose로 마이그레이션 하는 과정이 복잡하지 않을까?
2. 마이그레이션 이후 오히려 코드가 더 복잡해지지 않을까?
3. Compose로 레이아웃을 구성하는 것 힘들지 않을까?

결론부터 말하자면 세 가지 다 문제가 되지 않았다.

먼저 첫 번째 같은 경우, 가장 크게 고려했던 문제이다.
한 번에 모두 다 마이그레이션 하는 것은 아무래도 부담이 크기에, 되도록이면 이런 사태는 피하고 싶었다.
다행히 [공식 문서](https://developer.android.com/jetpack/compose/migrate/strategy)에 나온 것처럼 Compose와 기존의 View를 같은 레이아웃에서 동시에 사용 가능하여 큰 부담 없이 진행할 수 있었다.

두 번째로, Compose로 레이아웃을 구성하면 기존보다 복잡해질 것을 우려했는데, 괜한 걱정이었다 싶을 정도로 코드가 간결해졌다.
특히, 기존 코드에는 보일러 플레이트가 조금 많았는데, Compose로 마이그레이션 한 이후 획기적으로 줄일 수 있었다.

마지막으로 기존에 Compose를 사용했을 때, Column과 Row만으로 레이아웃을 구성하는 것이 많이 힘들었던 기억이 있었는데, 다행히도 Constraint Layout이 가능하다는 점을 발견했다. (전에는 없었던 것 같은데 다시 찾아보니 아니었다.)

그렇다고 해서 Compose가 무조건 좋나고 한다면, 그건 또 확신이 없다.
분명 장점이 많지만, 단점이 아주 없는 것은 아니다.

가장 크게 느꼈던 점이, Navigation Drawer를 구현할 때, 기존보다 Drawer가 조금 큰 게 느껴져 크기를 조절하고자 하였다.
width = 240.dp 이런 식으로 구현이 가능할 거라 생각을 했는데, 보기 좋게 틀렸다.

선언형 UI라 그런지, 이런 크기를 직접 지정하는 게 안 됐다...

아예 방법이 없는 것은 아니었지만, 방법이 복잡해서 그냥 후순위로 미뤄두었다.
물론 꾸준히 사용하다 보면 적응이 되겠지만, 아직 내게는 너무 낯선 문제였다...

그래도 Compose를 사용하는 게 그렇지 않은 것보단 장점이 많은 것 같아 다음 프로젝트도 Compose를 적극적으로 사용해 볼 생각이다.

아래는 Compose 마이그레이션 중인 Pull request다.
[Migrate to Jetpack Compose](https://github.com/kongwoojin/koreatech-board-client-android/pull/33)

---

원본 게시글: https://blog.kongjak.dev/30
