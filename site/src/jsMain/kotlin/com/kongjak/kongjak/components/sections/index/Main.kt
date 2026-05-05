package com.kongjak.kongjak.components.sections.index

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.Element
import org.jetbrains.compose.web.dom.B
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun IndexMain() {
    Section(attrs = { classes("hero") }) {
        Div(attrs = { classes("hero-meta") }) {
            Span(attrs = { classes("pill") }) {
                Span(attrs = { classes("led") }) {}
                Span(attrs = { classes("ok") }) { Text("ONLINE") }
            }
            Span { Text("KOREA · KST") }
            Span(attrs = { classes("mono") }) { Text("v2026.05") }
        }

        H1 {
            Span(attrs = { id("typedName") }) { Text("WooJin Kong") }
            Span(attrs = { classes("caret"); attr("aria-hidden", "true") }) {}
        }

        P(attrs = { classes("role", "mono") }) {
            Span(attrs = { classes("arrow") }) { Text("→") }
            Text(" Android Developer")
        }

        P(attrs = { classes("bio") }) {
            B { Text("Kotlin") }
            Text("과 ")
            B { Text("Jetpack Compose") }
            Text("로 안드로이드 앱을 만듭니다.")
        }
    }

    DisposableEffect(Unit) {
        val el = document.getElementById("typedName")
        val target = "WooJin Kong"
        el?.textContent = ""
        var i = 0
        var pending = 0
        var cancelled = false
        var tickFn: (() -> Unit)? = null
        tickFn = {
            if (!cancelled && i <= target.length) {
                el?.textContent = target.substring(0, i)
                i++
                pending = window.setTimeout({ tickFn?.invoke() }, (90 + Random.nextDouble() * 40).roundToInt())
            }
        }
        pending = window.setTimeout({ tickFn?.invoke() }, 250)
        onDispose {
            cancelled = true
            window.clearTimeout(pending)
            el?.textContent = target
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
