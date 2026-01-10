package com.kongjak.kongjak.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.right
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.silk.components.icons.fa.FaMoon
import com.varabyte.kobweb.silk.components.icons.fa.FaSun
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

private val accentColorLight = Color.rgb(0, 140, 160)
private val accentColorDark = Color.rgb(100, 200, 220)

val ColorModeButtonStyleLight =
    CssStyle {
        base {
            Modifier
                .padding(12.px)
                .borderRadius(50.percent)
                .cursor(Cursor.Pointer)
                .transition(Transition.of("all", 200.ms))
        }
        hover {
            Modifier
                .backgroundColor(Color.rgba(0, 140, 160, 0.2F))
                .color(accentColorLight)
        }
    }

val ColorModeButtonStyleDark =
    CssStyle {
        base {
            Modifier
                .padding(12.px)
                .borderRadius(50.percent)
                .cursor(Cursor.Pointer)
                .transition(Transition.of("all", 200.ms))
        }
        hover {
            Modifier
                .backgroundColor(Color.rgba(100, 200, 220, 0.2F))
                .color(accentColorDark)
        }
    }

@Composable
fun ColorModeButton() {
    var colorMode by ColorMode.currentState
    val buttonStyle = if (colorMode.isLight) ColorModeButtonStyleLight else ColorModeButtonStyleDark

    Box(
        modifier = Modifier
                .position(Position.Fixed)
                .top(24.px)
                .right(24.px)
                .zIndex(1000),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier =
                buttonStyle
                    .toModifier()
                    .onClick { colorMode = colorMode.opposite }
                    .color(
                        if (colorMode.isLight) {
                            Color.rgb(60, 60, 60)
                        } else {
                            Color.rgba(255, 255, 255, 0.7F)
                        },
                    ),
            contentAlignment = Alignment.Center,
        ) {
            if (colorMode.isLight) {
                FaMoon(size = IconSize.LG)
            } else {
                FaSun(size = IconSize.LG)
            }
        }
    }
}
