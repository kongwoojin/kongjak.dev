package com.kongjak.kongjak.components.sections

import androidx.compose.runtime.Composable
import com.kongjak.kongjak.generated.gitCommitHash
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.dom.Footer
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun SiteFooter(modifier: Modifier = Modifier) {
    Footer(attrs = modifier.toAttrs()) {
        Span { Text("© 2026 Kongjak") }
        Span(attrs = { classes("build") }) { Text("build · $gitCommitHash") }
    }
}
