package dev.kongjak.kongjak.components.sections.index

import androidx.compose.runtime.Composable
import dev.kongjak.kongjak.utils.GITHUB_URL
import dev.kongjak.kongjak.utils.MAIL_ADDRESS
import org.jetbrains.compose.web.dom.*

@Composable
fun IndexMain() {
    Section(attrs = { classes("hero"); attr("aria-labelledby", "intro-title") }) {
        Div(attrs = { classes("hero-copy") }) {
            P(attrs = { classes("eyebrow") }) { Text("Android 개발자") }
            H1(attrs = { id("intro-title") }) { Text("공우진") }
            P(attrs = { classes("hero-description") }) {
                Text("한국기술교육대학교 컴퓨터공학부에 재학 중입니다.")
                Br()
                Text("Android 애플리케이션 개발을 공부 중입니다.")
            }
            Div(attrs = { classes("hero-links") }) {
                A(href = MAIL_ADDRESS, attrs = { classes("quiet-link") }) { Text("이메일") }
                A(href = GITHUB_URL, attrs = { classes("quiet-link"); attr("target", "_blank"); attr("rel", "noopener noreferrer") }) { Text("GitHub ↗") }
                A(href = "/resume.pdf", attrs = { classes("quiet-link"); attr("target", "_blank"); attr("rel", "noopener noreferrer") }) { Text("이력서 ↗") }
            }
        }
    }
}
