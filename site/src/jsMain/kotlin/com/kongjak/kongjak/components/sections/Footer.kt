package com.kongjak.kongjak.components.sections

import androidx.compose.runtime.Composable
import com.kongjak.kongjak.utils.GITHUB_URL
import com.kongjak.kongjak.utils.MAIL_ADDRESS
import com.kongjak.kongjak.utils.TELEGRAM_URL
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaEnvelope
import com.varabyte.kobweb.silk.components.icons.fa.FaGithub
import com.varabyte.kobweb.silk.components.icons.fa.FaPaperPlane
import com.varabyte.kobweb.silk.components.icons.fa.IconSize.LG
import com.varabyte.kobweb.silk.components.icons.fa.IconStyle.FILLED
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.navigation.Link
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import kotlin.js.Date

@Composable
fun Footer(modifier: Modifier = Modifier) {
    val currentDate = Date()
    SimpleGrid(
        modifier = Modifier.padding(16.px).then(modifier).id("footer"),
        numColumns = numColumns(1, lg = 2)
    ) {
        Span(attrs = Modifier.margin(topBottom = 4.px).fontSize(16.px).toAttrs()) {
            Text("Copyright © 2024 - ${currentDate.getFullYear()} Kongjak. All rights reserved.")
        }

        Row(
            modifier = Modifier.margin(topBottom = 4.px),
            horizontalArrangement = Arrangement.End
        ) {
            Link(TELEGRAM_URL, Modifier.margin(leftRight = 8.px).color(Colors.White)) {
                FaPaperPlane(size = LG, style = FILLED)
            }

            Link(GITHUB_URL, Modifier.margin(leftRight = 8.px).color(Colors.White)) {
                FaGithub(size = LG)
            }
            Link(MAIL_ADDRESS, Modifier.margin(leftRight = 8.px).color(Colors.White)) {
                FaEnvelope(size = LG, style = FILLED)
            }
        }
    }
}
