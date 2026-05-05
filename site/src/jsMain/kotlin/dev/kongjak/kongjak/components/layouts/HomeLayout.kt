package dev.kongjak.kongjak.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import dev.kongjak.kongjak.components.sections.NavHeader
import dev.kongjak.kongjak.components.sections.SiteFooter
import dev.kongjak.kongjak.components.sections.TweaksPanel
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.data.getValue
import com.varabyte.kobweb.core.layout.Layout
import kotlinx.browser.document
import org.jetbrains.compose.web.dom.Main

@Composable
@Layout
fun HomeLayout(ctx: PageContext, content: @Composable ColumnScope.() -> Unit) {
    val data = ctx.data.getValue<PageLayoutData>()
    LaunchedEffect(data.title) {
        document.title = "Kongjak - ${data.title}"
    }
    NavHeader()
    Main(attrs = { id("top"); classes("shell") }) {
        with(PageContentScope) { content() }
        SiteFooter()
    }
    TweaksPanel(showDensity = true)
}
