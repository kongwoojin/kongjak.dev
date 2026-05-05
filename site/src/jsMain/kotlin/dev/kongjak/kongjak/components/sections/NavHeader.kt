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

sealed class NavStyle {
    /** Home-page nav with hash anchors (#skills, #projects, #contributions, #writing, #contact). */
    object Home : NavStyle()

    /** Blog-style nav with breadcrumb and links back to portfolio sections. */
    data class Blog(val crumb: List<CrumbPart>) : NavStyle()
}

sealed class CrumbPart {
    data class Active(val text: String) : CrumbPart()
    data class Link(val text: String, val href: String) : CrumbPart()
}

@Composable
fun NavHeader(style: NavStyle = NavStyle.Home) {
    var colorMode by ColorMode.currentState
    Header(attrs = { classes("topbar") }) {
        Div(attrs = { classes("topbar-inner") }) {
            A(href = if (style is NavStyle.Home) "#top" else "/", attrs = { classes("brand") }) {
                Span(attrs = { classes("dot"); attr("aria-hidden", "true") }) {}
                B { Text("kongjak") }
                Span { Text(".dev") }
            }
            when (style) {
                is NavStyle.Home -> HomeNav()
                is NavStyle.Blog -> BlogNav(style.crumb)
            }
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
        HashLink("#skills", "skills")
        HashLink("#projects", "projects")
        HashLink("#contributions", "contributions")
        HashLink("#writing", "writing")
        HashLink("#contact", "contact")
    }
}

@Composable
private fun BlogNav(crumb: List<CrumbPart>) {
    Span(attrs = { classes("crumb", "mono") }) {
        crumb.forEach { part ->
            Span(attrs = { classes("sep") }) { Text("/") }
            when (part) {
                is CrumbPart.Active -> B { Text(part.text) }
                is CrumbPart.Link -> A(href = part.href) { Text(part.text) }
            }
        }
    }
    Nav(attrs = { classes("nav"); attr("aria-label", "섹션 이동") }) {
        HashLink("/#projects", "projects")
        HashLink("/#contact", "contact")
    }
}
