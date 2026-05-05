package com.kongjak.kongjak.components.sections.index

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun SectionHead(num: String, title: String) {
    Div(attrs = { classes("section-head") }) {
        Span(attrs = { classes("num") }) { Text(num) }
        H2 { Text(title) }
        Span(attrs = { classes("rule") }) {}
    }
}
