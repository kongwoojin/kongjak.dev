package dev.kongjak.kongjak.components.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.silk.components.icons.fa.FaMoon
import com.varabyte.kobweb.silk.components.icons.fa.FaSun
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import dev.kongjak.kongjak.utils.BLOG_URL
import org.jetbrains.compose.web.dom.*

@Composable
fun NavHeader() {
    var colorMode by ColorMode.currentState
    Header(attrs = { classes("topbar") }) {
        Div(attrs = { classes("topbar-inner") }) {
            A(href = "/#top", attrs = { classes("brand"); attr("aria-label", "공우진 포트폴리오 처음으로") }) {
                Text("kongjak")
                Span { Text(".") }
            }
            Nav(attrs = { classes("nav"); attr("aria-label", "섹션 이동") }) {
                A(href = "/#projects") { Text("프로젝트") }
                A(href = "/#skills") { Text("기술 스택") }
                A(href = "/#about") { Text("소개") }
                A(href = BLOG_URL, attrs = { attr("target", "_blank"); attr("rel", "noopener noreferrer") }) { Text("블로그") }
            }
            Button(attrs = {
                classes("theme-toggle")
                attr("type", "button")
                attr("aria-label", if (colorMode.isLight) "어두운 테마로 전환" else "밝은 테마로 전환")
                onClick { colorMode = colorMode.opposite }
            }) {
                if (colorMode.isLight) FaMoon() else FaSun()
            }
        }
    }
}
