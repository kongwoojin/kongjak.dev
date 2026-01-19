package com.kongjak.kongjak.components.sections.index

import androidx.compose.runtime.Composable
import com.kongjak.kongjak.models.Project
import com.kongjak.kongjak.models.ProjectPart
import com.kongjak.kongjak.models.getFaIcon
import com.kongjak.kongjak.toSitePalette
import com.kongjak.kongjak.utils.DevIcon
import com.kongjak.kongjak.utils.projectLists
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderBottom
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

val ProjectsSectionHeaderStyle = CssStyle.base {
    Modifier
        .fontSize(0.9.cssRem)
        .fontWeight(FontWeight.Bold)
        .color(colorMode.toSitePalette().accent)
        .letterSpacing(0.15.em)
        .margin(bottom = 64.px)
        .textAlign(TextAlign.Center)
}


val ProjectCardStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .padding(32.px)
            .borderRadius(16.px)
            .backgroundColor(colorMode.toSitePalette().cardBackground)
            .border(1.px, LineStyle.Solid, colorMode.toSitePalette().divider)
            .margin(bottom = 24.px)
            .transition(Transition.of("all", 300.ms))
    }
    hover {
        Modifier.border(1.px, LineStyle.Solid, colorMode.toSitePalette().accent)
    }
}

val ProjectTitleStyle = CssStyle.base {
    Modifier
        .color(colorMode.toSitePalette().title)
        .fontWeight(FontWeight.Bold)
        .margin(bottom = 4.px, top = 0.px)
        .fontSize(1.5.cssRem)
        .letterSpacing((-0.02).em)
}

val ProjectPositionStyle = CssStyle.base {
    Modifier
        .color(colorMode.toSitePalette().accent)
        .fontSize(0.85.cssRem)
        .fontWeight(FontWeight.Medium)
        .margin(bottom = 12.px, top = 0.px)
}

val ProjectDescriptionStyle = CssStyle.base {
    Modifier
        .color(colorMode.toSitePalette().description)
        .fontSize(0.95.cssRem)
        .lineHeight(1.7)
        .margin(bottom = 24.px, top = 0.px)
}

val PartListItemStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .padding(16.px, 0.px)
        .borderBottom(1.px, LineStyle.Solid, colorMode.toSitePalette().divider)
}

val PartListTitleStyle = CssStyle.base {
    Modifier
        .color(colorMode.toSitePalette().title)
        .fontSize(0.9.cssRem)
        .fontWeight(FontWeight.Medium)
        .margin(bottom = 6.px)
}

@Composable
fun IndexProjects() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .minHeight(100.vh)
            .id("projects")
            .padding(topBottom = 100.px, leftRight = 24.px),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().maxWidth(800.px),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            H2(attrs = ProjectsSectionHeaderStyle.toAttrs()) {
                Text("PROJECTS")
            }

            for (project in projectLists) {
                ProjectWidget(project)
            }
        }
    }
}

@Composable
fun ProjectWidget(project: Project) {
    Column(
        modifier = ProjectCardStyle.toModifier(),
    ) {
        H3(attrs = ProjectTitleStyle.toAttrs()) {
            Text(project.name)
        }
        project.position?.let { position ->
            P(attrs = ProjectPositionStyle.toAttrs()) {
                Text(position)
            }
        }
        P(attrs = ProjectDescriptionStyle.toAttrs()) {
            Text(project.description)
        }

        PartsListStyle(project.parts)
    }
}

@Composable
fun PartsListStyle(parts: List<ProjectPart>) {
    val colorMode = ColorMode.current
    val techStackTextColor = colorMode.toSitePalette().description
    val linkAccentColor = colorMode.toSitePalette().accent

    Column(modifier = Modifier.fillMaxWidth()) {
        for (part in parts) {
            Column(modifier = PartListItemStyle.toModifier()) {
                Span(attrs = PartListTitleStyle.toAttrs()) {
                    Text(part.name)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    if (part.techStacks.isNotEmpty()) {
                        Row(
                            modifier = Modifier.margin(right = 16.px),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            for (techStack in part.techStacks) {
                                Row(
                                    modifier = Modifier.margin(right = 12.px),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    DevIcon(
                                        modifier = Modifier.fontSize(0.8.cssRem).margin(right = 6.px),
                                        icon = techStack.icon
                                    )
                                    SpanText(
                                        text = techStack.name,
                                        modifier = Modifier.Companion
                                            .color(techStackTextColor)
                                            .fontSize(0.8.cssRem),
                                    )
                                }
                            }
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        for (url in part.urls) {
                            Link(
                                path = url.url,
                                modifier = Modifier.Companion
                                    .color(linkAccentColor)
                                    .fontSize(0.8.cssRem)
                                    .margin(right = 16.px)
                                    .opacity(0.7F)
                                    .transition(Transition.of("opacity", 150.ms)),
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    getFaIcon(url.icon)
                                    SpanText(
                                        text = url.urlName,
                                        modifier = Modifier.margin(left = 6.px),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
