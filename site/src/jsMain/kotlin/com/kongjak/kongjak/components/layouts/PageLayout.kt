package com.kongjak.kongjak.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.kongjak.kongjak.components.sections.Footer
import com.kongjak.kongjak.components.widgets.ColorModeButton
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gridRow
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateRows
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.document
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.percent

private val darkBgColor = Color.rgb(12, 12, 12)
private val lightBgColor = Color.rgb(250, 250, 250)

@Composable
fun PageLayout(
    title: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colorMode = ColorMode.current
    val bgColor = if (colorMode.isLight) lightBgColor else darkBgColor

    LaunchedEffect(title) {
        document.title = "Kongjak - $title"
    }

    ColorModeButton()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .minHeight(100.percent)
            .backgroundColor(bgColor)
            .gridTemplateRows {
                size(1.fr)
                size(minContent)
            },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxSize().gridRow(1),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                content()
            }
            Footer()
        }
    }
}
