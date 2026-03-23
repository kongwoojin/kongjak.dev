---
layout: .components.layouts.BlogLayout
title: "Compose contentDescription null과 \"\"의 차이"
date: "2025-10-20"
description: ""
tags: ["android", "compose", "jetpack-compose"]
---

# Compose contentDescription null과 ""의 차이

Compose에서 Image나 Icon과 같이, 이미지를 보여주는 컴포넌트를 사용할 때는 contentDescription을 사용해야 한다.

이 contentDescription이 무엇인지 정확하게 알아보기 위해, Image Composable의 주석을 읽어보았다.

<blockquote>
@param contentDescription text used by accessibility services to describe what this image<br />
represents. This should always be provided unless this image is used for decorative purposes,<br />
and does not represent a meaningful action that a user can take. This text should be localized,<br />
such as by using [androidx.compose.ui.res.stringResource] or similar
</blockquote>

간단하게 말해서, 접근성 서비스에서 사용할 이미지에 대한 설명을 나타내는 것이 contentDescription이다.

그렇기 때문에, 주석에 적힌 것과 같이, 해당 텍스트는 지역화 또한 진행하여야 한다.

하지만 필자는 개발하면서 모든 이미지에 contentDescription을 작성하지는 못하고 있다.

여러 이유가 있겠지만, 모든 이미지에 contentDescription을 선언하지 못할 정도로 바쁠때도 있고, 선언하려고 해도 설명이 애매한 경우도 있다. <del>귀찮은 것도 있다.</del>

이런 경우에서 지금까지는 contentDescription=null로 처리하고 있었다.

다만 최근 팀원의 PR을 리뷰하는 도중, contentDescription=""로 작성한 경우를 보게 되었다.

팀원마다 contentDescription을 "'로 작성하기도 하고, null로 작성하기도 하는 상황에서 어떤 것이 맞는 선택지인지 확신이 들지 않아서 Compose 코드를 열어봤다.

```
@Composable
fun Image(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
    val semantics =
        if (contentDescription != null) {
            Modifier.semantics {
                this.contentDescription = contentDescription
                this.role = Role.Image
            }
        } else {
            Modifier
        }

    // Explicitly use a simple Layout implementation here as Spacer squashes any non fixed
    // constraint with zero
    Layout(
        modifier
            .then(semantics)
            .clipToBounds()
            .paint(
                painter,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter,
            )
    ) { _, constraints ->
        layout(constraints.minWidth, constraints.minHeight) {}
    }
```

compose.foundation의 Image 컴포넌트 코드이다.

이 중 주의 깊게 봐야 할 코드는 아래와 같다.

```
val semantics =
        if (contentDescription != null) {
            Modifier.semantics {
                this.contentDescription = contentDescription
                this.role = Role.Image
            }
        } else {
            Modifier
        }
```

contentDescription이 null인지를 확인하고, null이 아닐 경우 semantics라는 Modifier 옵션을 적용하고 있다.

반대로, null 일 경우 빈 Modifier를 사용하고 있다.

그렇다면, 이 semantics modifier는 어떤 역할을 하는 것일까

[Semantics 공식 문서](https://developer.android.com/develop/ui/compose/accessibility/semantics)에는 아래와 같이 설명하고 있다.

> Information about the meaning and role of a component in Compose is called **semantics**, which are a way to provide additional context about composables to services like accessibility, autofill, and testing. For example, a camera icon might visually just be an image, but the semantic meaning could be "Take a photo".

Compose에서 컴포넌트의 역할과 같은 정보를 semantics라고 하고, 접근성 혹은 테스트와 같은 서비스에 추가적인 정보를 제공한다고 한다.

그렇다면 결국 우리가 선언한 contentDescription을 semantics modifier를 통해 정의하는 것이라고 이해하면 될 것 같다.

그럼 contentDescription 아래에 있는 Role은 무엇일까

먼저 compose.foundation의 [SemanticsProperties.kt](https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/ui/ui/src/commonMain/kotlin/androidx/compose/ui/semantics/SemanticsProperties.kt;l=689?q=Role&sq=&ss=androidx%2Fplatform%2Fframeworks%2Fsupport:compose%2F) 에 선언되어 있는 Role의 주석을 읽어보자

<blockquote>
/**<br />
* The type of user interface element. Accessibility services might use this to describe the element<br />
* or do customizations. Most roles can be automatically resolved by the semantics properties of<br />
* this element. But some elements with subtle differences need an exact role. If an exact role is<br />
* not listed, [SemanticsPropertyReceiver.role] should not be set and the framework will<br />
* automatically resolve it.<br />
*/
</blockquote>

사용자 인터페이스의 타입이라고 설명되어 있긴 하지만, 주석만으로는 이해가 가지 않는다. 코드를 살펴보는 것이 좋을 것 같다.

```
/**
 * This element is a button control. Associated semantics properties for accessibility:
 * [SemanticsProperties.Disabled], [SemanticsActions.OnClick]
 */
val Button = Role(0)

/**
 * This element is a Checkbox which is a component that represents two states (checked /
 * unchecked). Associated semantics properties for accessibility:
 * [SemanticsProperties.Disabled], [SemanticsProperties.StateDescription],
 * [SemanticsActions.OnClick]
 */
val Checkbox = Role(1)

/**
 * This element is a Switch which is a two state toggleable component that provides on/off
 * like options. Associated semantics properties for accessibility:
 * [SemanticsProperties.Disabled], [SemanticsProperties.StateDescription],
 * [SemanticsActions.OnClick]
 */
val Switch = Role(2)

/**
 * This element is a RadioButton which is a component to represent two states, selected and
 * not selected. Associated semantics properties for accessibility:
 * [SemanticsProperties.Disabled], [SemanticsProperties.StateDescription],
 * [SemanticsActions.OnClick]
 */
val RadioButton = Role(3)

/**
 * This element is a Tab which represents a single page of content using a text label and/or
 * icon. A Tab also has two states: selected and not selected. Associated semantics
 * properties for accessibility: [SemanticsProperties.Disabled],
 * [SemanticsProperties.StateDescription], [SemanticsActions.OnClick]
 */
val Tab = Role(4)

/**
 * This element is an image. Associated semantics properties for accessibility:
 * [SemanticsProperties.ContentDescription]
 */
val Image = Role(5)
```

코드를 통해 알 수 있듯, 각 Composable이 어떤 종류인지 나타내는 것이 Role이고 이것을 접근성 서비스에서 읽어서 처리하는 것이다.

따라서 컴포넌트마다 적절한 Role을 선언하다는 것이 필요하다는 것을 알 수 있다.

물론 주석에 나와있는 것과 같이, 대부분의 경우 적절한 Role이 자동으로 적용된다고 한다. (아마 코드를 까고 들어갈 수록 Role이 어딘가에 선언되어 있지 않을까 싶다)

그렇다면 처음으로 돌아가서, Image의 contentDescription을 null로 설정하는 것과, ""로 설정하는 것의 차이를 이제는 알 수 있다.

contentDescription이 null일 경우, 빈 Modifier가 적용되지만, contentDescription이 ""일 경우, semantics modifier를 통해 적절한 Role이 함께 설정된다.

물론 contentDescription을 명확하게 작성하는 것이 좋겠지만, 피치 못할 경우, null로 선언하는 것 보다 ""이 조금은 더 나은 선택이 아닐까 생각한다.

---

원본 게시글: https://blog.kongjak.dev/32
