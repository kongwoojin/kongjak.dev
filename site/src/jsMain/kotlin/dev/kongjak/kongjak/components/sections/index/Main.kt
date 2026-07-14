package dev.kongjak.kongjak.components.sections.index

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import kotlinx.browser.document
import org.w3c.dom.Element
import org.jetbrains.compose.web.dom.B
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun IndexMain() {
    Section(attrs = { classes("hero") }) {
        Div(attrs = { classes("hero-copy") }) {
            Div(attrs = { classes("hero-kicker") }) {
                Span(attrs = { classes("status-dot") }) {}
                Text("Android Developer · Kotlin · Compose")
            }
            H1 { Text("WooJin Kong") }
            P(attrs = { classes("hero-about") }) {
                Text("Kotlin과 Jetpack Compose를 주로 사용합니다. 기능 개발, 앱 구조 개선, 테스트와 배포 자동화를 해왔습니다.")
            }
            Div(attrs = { classes("hero-actions") }) {
                A(href = "#engineering", attrs = { classes("primary-action") }) { Text("작업 보기") }
                A(href = "/resume.pdf", attrs = { classes("text-action"); attr("target", "_blank") }) { Text("이력서 ↗") }
                A(href = "https://github.com/kongwoojin", attrs = { classes("text-action"); attr("target", "_blank"); attr("rel", "noopener") }) { Text("GitHub ↗") }
            }
            Div(attrs = { classes("hero-proof") }) {
                Div { B { Text("2017 —") }; Span { Text("Building Android software") } }
                Div { B { Text("77") }; Span { Text("Public repositories") } }
            }
        }

        Div(attrs = { classes("hero-product") }) {
            Div(attrs = { classes("scope-head") }) {
                Span { Text("WHAT I WORK ON") }
                B { Text("주로 이런 일을 합니다.") }
            }
            Div(attrs = { classes("scope-list") }) {
                Scope("01", "App development", "Android 기능과 화면을 개발합니다.")
                Scope("02", "Architecture", "기존 구조를 정리하고 공통 코드를 개선합니다.")
                Scope("03", "AOSP & Linux", "커스텀 ROM과 디바이스 트리를 다룹니다.")
                Scope("04", "Automation", "테스트, CI와 반복 작업을 자동화합니다.")
            }
        }
    }

    DisposableEffect(Unit) {
        val elements = document.querySelectorAll(".reveal")
        for (idx in 0 until elements.length) {
            (elements.item(idx) as? Element)?.classList?.add("in")
        }
        onDispose {}
    }
}

@Composable
private fun Scope(index: String, title: String, detail: String) {
    Div(attrs = { classes("scope-row") }) {
        Span { Text(index) }
        Div { B { Text(title) }; P { Text(detail) } }
    }
}
