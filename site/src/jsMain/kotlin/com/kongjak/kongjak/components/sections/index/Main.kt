package com.kongjak.kongjak.components.sections.index

import androidx.compose.runtime.Composable
import com.kongjak.kongjak.components.widgets.TextTooltip
import com.kongjak.kongjak.utils.BLOG_URL
import com.kongjak.kongjak.utils.GITHUB_URL
import com.kongjak.kongjak.utils.MAIL_ADDRESS
import com.kongjak.kongjak.utils.TELEGRAM_URL
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaBlog
import com.varabyte.kobweb.silk.components.icons.fa.FaEnvelope
import com.varabyte.kobweb.silk.components.icons.fa.FaGithub
import com.varabyte.kobweb.silk.components.icons.fa.FaPaperPlane
import com.varabyte.kobweb.silk.components.icons.fa.IconSize.XL
import com.varabyte.kobweb.silk.components.icons.fa.IconStyle.FILLED
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

private val accentColor = Color.rgb(100, 200, 220)

val HeroTitleStyle =
    CssStyle {
        base {
            Modifier
                .fontSize(4.cssRem)
                .fontWeight(FontWeight.Bold)
                .color(Colors.White)
                .letterSpacing((-0.03).em)
                .margin(bottom = 16.px)
        }
    }

val HeroSubtitleStyle =
    CssStyle {
        base {
            Modifier
                .fontSize(1.2.cssRem)
                .fontWeight(FontWeight.Normal)
                .color(Color.rgba(255, 255, 255, 0.5F))
                .letterSpacing(0.02.em)
                .margin(bottom = 48.px)
        }
    }

val SocialLinkStyle =
    CssStyle {
        base {
            Modifier
                .color(Color.rgba(255, 255, 255, 0.5F))
                .padding(16.px)
                .margin(leftRight = 8.px)
                .transition(Transition.of("all", 200.ms))
        }
        hover {
            Modifier.color(accentColor)
        }
    }

@Composable
fun IndexMain() {
    Column(
        modifier =
            Modifier
                .minWidth(100.percent)
                .minHeight(100.vh)
                .id("main")
                .padding(24.px),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        H1(
            attrs =
                Modifier
                    .fontSize(4.cssRem)
                    .fontWeight(FontWeight.Bold)
                    .color(Colors.White)
                    .letterSpacing((-0.03).em)
                    .margin(bottom = 16.px, top = 0.px)
                    .toAttrs(),
        ) {
            Text("Kongjak")
        }

        P(
            attrs =
                Modifier
                    .fontSize(1.2.cssRem)
                    .fontWeight(FontWeight.Normal)
                    .color(Color.rgba(255, 255, 255, 0.5F))
                    .letterSpacing(0.02.em)
                    .margin(bottom = 48.px, top = 0.px)
                    .toAttrs(),
        ) {
            Text("Android Developer")
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Link(TELEGRAM_URL, SocialLinkStyle.toModifier()) {
                FaPaperPlane(size = XL, style = FILLED)
            }
            TextTooltip("Telegram")

            Link(GITHUB_URL, SocialLinkStyle.toModifier()) {
                FaGithub(size = XL)
            }
            TextTooltip("GitHub")

            Link(BLOG_URL, SocialLinkStyle.toModifier()) {
                FaBlog(size = XL)
            }
            TextTooltip("Blog")

            Link(MAIL_ADDRESS, SocialLinkStyle.toModifier()) {
                FaEnvelope(size = XL, style = FILLED)
            }
            TextTooltip("Email")
        }
    }
}
