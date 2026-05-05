package dev.kongjak.kongjak.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.browser.dom.ElementTarget
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.overlay.AdvancedTooltip
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun TextTooltip(
    text: String,
    target: ElementTarget = ElementTarget.PreviousSibling,
    modifier: Modifier = Modifier,
    hasArrow: Boolean = false
) {
    AdvancedTooltip(
        target = target,
        hasArrow = hasArrow,
        modifier = Modifier.backgroundColor(Colors.Gray).padding(4.px).then(
            modifier
        ),
        content = {
            Span(attrs = Modifier.color(Colors.White).fontSize(12.px).toAttrs()) {
                Text(text)
            }
        }
    )
}