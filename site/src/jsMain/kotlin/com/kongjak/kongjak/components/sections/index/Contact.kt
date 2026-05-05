package com.kongjak.kongjak.components.sections.index

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import kotlinx.browser.document
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun IndexContact() {
    Section(attrs = { id("contact") }) {
        SectionHead("05", "contact")

        Div(attrs = { classes("contact-block", "reveal") }) {
            Div(attrs = { classes("contact-tabbar") }) {
                Span(attrs = { classes("dots") }) {
                    Span(attrs = { classes("mac-dot") }) {}
                    Span(attrs = { classes("mac-dot") }) {}
                    Span(attrs = { classes("mac-dot") }) {}
                }
                Span { Text("~/") }
            }
            Div(attrs = { classes("contact-body") }) {
                Div {
                    Span(attrs = { classes("pl") }) { Text("kongjak@dev") }
                    Text(" ~ ")
                    Span(attrs = { classes("cmd") }) { Text("$ cat contact.txt") }
                }
                Span(attrs = { classes("out") }) {
                    Span(attrs = { classes("key") }) { Text("email   ") }
                    Text(" · ")
                    A(href = "mailto:kongjak@kongjak.dev") { Text("kongjak@kongjak.dev") }
                }
                Span(attrs = { classes("out") }) {
                    Span(attrs = { classes("key") }) { Text("github  ") }
                    Text(" · ")
                    A(href = "https://github.com/kongwoojin", attrs = { attr("target", "_blank"); attr("rel", "noopener") }) {
                        Text("github.com/kongwoojin")
                    }
                }
                Span(attrs = { classes("out") }) {
                    Span(attrs = { classes("key") }) { Text("telegram") }
                    Text(" · ")
                    A(href = "https://t.me/Kongjak", attrs = { attr("target", "_blank"); attr("rel", "noopener") }) {
                        Text("t.me/Kongjak")
                    }
                }
                Span(attrs = { classes("out") }) {
                    Span(attrs = { classes("key") }) { Text("blog    ") }
                    Text(" · ")
                    A(href = "https://blog.kongjak.dev", attrs = { attr("target", "_blank"); attr("rel", "noopener") }) {
                        Text("blog.kongjak.dev")
                    }
                }
                Span(attrs = { classes("out") }) {
                    Span(attrs = { classes("key") }) { Text("site    ") }
                    Text(" · ")
                    A(href = "https://kongjak.dev", attrs = { attr("target", "_blank"); attr("rel", "noopener") }) {
                        Text("kongjak.dev")
                    }
                }
                Div(attrs = { style { property("margin-top", "12px") } }) {
                    Span(attrs = { classes("pl") }) { Text("kongjak@dev") }
                    Text(" ~ ")
                    Span(attrs = { classes("cmd") }) {
                        Text("$ ")
                        Span(attrs = { id("lastCmd") }) { Text("_") }
                    }
                }
            }
        }
    }

    DisposableEffect(Unit) {
        val el = document.getElementById("lastCmd")
        var on = true
        val handle = kotlinx.browser.window.setInterval({
            on = !on
            el?.textContent = if (on) "_" else " "
        }, 540)
        onDispose { kotlinx.browser.window.clearInterval(handle) }
    }
}
