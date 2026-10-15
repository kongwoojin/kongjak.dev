package dev.kongjak.kongjak.components.sections

import androidx.compose.runtime.Composable
import dev.kongjak.kongjak.utils.GITHUB_URL
import dev.kongjak.kongjak.utils.MAIL_ADDRESS
import org.jetbrains.compose.web.dom.*

@Composable
fun SiteFooter() {
    Footer(attrs = { classes("site-footer") }) {
        Div {
            A(href = MAIL_ADDRESS, attrs = { classes("footer-email") }) { Text("kongjak@kongjak.dev ↗") }
        }
        Div(attrs = { classes("footer-bottom") }) {
            Span { Text("Copyright WooJin Kong") }
            Div {
                A(href = GITHUB_URL, attrs = { attr("target", "_blank"); attr("rel", "noopener noreferrer") }) { Text("GitHub ↗") }
                A(href = "#top") { Text("맨 위로 ↑") }
            }
        }
    }
}
