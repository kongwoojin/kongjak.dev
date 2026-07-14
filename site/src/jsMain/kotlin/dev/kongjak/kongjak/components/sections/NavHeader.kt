package dev.kongjak.kongjak.components.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.silk.components.icons.fa.FaMoon
import com.varabyte.kobweb.silk.components.icons.fa.FaSun
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.B
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Header
import org.jetbrains.compose.web.dom.Nav
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun NavHeader() {
    var colorMode by ColorMode.currentState
    Header(attrs = { classes("topbar") }) {
        Div(attrs = { classes("topbar-inner") }) {
            A(href = "#top", attrs = { classes("brand") }) {
                Span(attrs = { classes("dot"); attr("aria-hidden", "true") }) {}
                B { Text("kongjak") }
                Span { Text(".dev") }
            }
            HomeNav()
            Button(attrs = {
                classes("theme-toggle")
                attr("aria-label", "테마 전환")
                onClick { colorMode = colorMode.opposite }
            }) {
                if (colorMode.isLight) FaSun() else FaMoon()
            }
        }
    }
}

@Composable
private fun HashLink(href: String, label: String) {
    A(href = href) {
        Span(attrs = { classes("hash") }) { Text("#") }
        Text(label)
    }
}

@Composable
private fun HomeNav() {
    Nav(attrs = { classes("nav"); attr("aria-label", "섹션 이동") }) {
        HashLink("#engineering", "notes")
        HashLink("#projects", "projects")
        HashLink("#contact", "contact")
    }
}
