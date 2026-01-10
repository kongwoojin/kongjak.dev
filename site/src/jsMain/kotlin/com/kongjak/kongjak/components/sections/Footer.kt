package com.kongjak.kongjak.components.sections

import androidx.compose.runtime.Composable
import com.kongjak.kongjak.utils.BLOG_URL
import com.kongjak.kongjak.utils.GITHUB_URL
import com.kongjak.kongjak.utils.MAIL_ADDRESS
import com.kongjak.kongjak.utils.TELEGRAM_URL
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.borderTop
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaBlog
import com.varabyte.kobweb.silk.components.icons.fa.FaEnvelope
import com.varabyte.kobweb.silk.components.icons.fa.FaGithub
import com.varabyte.kobweb.silk.components.icons.fa.FaPaperPlane
import com.varabyte.kobweb.silk.components.icons.fa.IconSize.LG
import com.varabyte.kobweb.silk.components.icons.fa.IconStyle.FILLED
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import kotlin.js.Date

private val accentColorLight = Color.rgb(0, 140, 160)
private val accentColorDark = Color.rgb(100, 200, 220)

val FooterLinkStyleLight =
    CssStyle {
        base {
            Modifier
                .color(Color.rgba(0, 0, 0, 0.4F))
                .padding(12.px)
                .transition(Transition.of("color", 200.ms))
        }
        hover {
            Modifier.color(accentColorLight)
        }
    }

val FooterLinkStyleDark =
    CssStyle {
        base {
            Modifier
                .color(Color.rgba(255, 255, 255, 0.4F))
                .padding(12.px)
                .transition(Transition.of("color", 200.ms))
        }
        hover {
            Modifier.color(accentColorDark)
        }
    }

@Composable
fun Footer(modifier: Modifier = Modifier) {
    val colorMode = ColorMode.current
    val borderColor = if (colorMode.isLight) Color.rgba(0, 0, 0, 0.1F) else Color.rgba(255, 255, 255, 0.08F)
    val copyrightColor = if (colorMode.isLight) Color.rgba(0, 0, 0, 0.4F) else Color.rgba(255, 255, 255, 0.3F)
    val footerLinkStyle = if (colorMode.isLight) FooterLinkStyleLight else FooterLinkStyleDark
    val currentDate = Date()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .borderTop(1.px, LineStyle.Solid, borderColor)
            .padding(topBottom = 40.px, leftRight = 24.px)
            .id("footer")
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().maxWidth(720.px),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.margin(bottom = 24.px),
                horizontalArrangement = Arrangement.Center,
            ) {
                Link(TELEGRAM_URL, footerLinkStyle.toModifier()) {
                    FaPaperPlane(size = LG, style = FILLED)
                }

                Link(GITHUB_URL, footerLinkStyle.toModifier()) {
                    FaGithub(size = LG)
                }

                Link(BLOG_URL, footerLinkStyle.toModifier()) {
                    FaBlog(size = LG)
                }

                Link(MAIL_ADDRESS, footerLinkStyle.toModifier()) {
                    FaEnvelope(size = LG, style = FILLED)
                }
            }

            Span(
                attrs =
                    Modifier
                        .fontSize(0.85.cssRem)
                        .color(copyrightColor)
                        .toAttrs(),
            ) {
                Text("© ${currentDate.getFullYear()} Kongjak")
            }
        }
    }
}
