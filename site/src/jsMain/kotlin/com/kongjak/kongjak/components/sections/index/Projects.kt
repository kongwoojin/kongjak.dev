package com.kongjak.kongjak.components.sections.index

import androidx.compose.runtime.Composable
import com.kongjak.kongjak.models.Project
import com.kongjak.kongjak.models.ProjectPart
import com.kongjak.kongjak.models.getFaIcon
import com.kongjak.kongjak.utils.DevIcon
import com.kongjak.kongjak.utils.projectLists
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
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

private val accentColorLight = Color.rgb(0, 140, 160)
private val accentColorDark = Color.rgb(100, 200, 220)

val ProjectsSectionHeaderStyleLight =
    CssStyle {
        base {
            Modifier
                .fontSize(0.9.cssRem)
                .fontWeight(FontWeight.Bold)
                .color(accentColorLight)
                .letterSpacing(0.15.em)
                .margin(bottom = 64.px)
                .textAlign(TextAlign.Center)
        }
    }

val ProjectsSectionHeaderStyleDark =
    CssStyle {
        base {
            Modifier
                .fontSize(0.9.cssRem)
                .fontWeight(FontWeight.Bold)
                .color(accentColorDark)
                .letterSpacing(0.15.em)
                .margin(bottom = 64.px)
                .textAlign(TextAlign.Center)
        }
    }

val ProjectCardStyleLight =
    CssStyle {
        base {
            Modifier
                .fillMaxWidth()
                .padding(32.px)
                .borderRadius(16.px)
                .backgroundColor(Color.rgba(0, 0, 0, 0.03F))
                .border(1.px, LineStyle.Solid, Color.rgba(0, 0, 0, 0.08F))
                .margin(bottom = 24.px)
                .transition(Transition.of("all", 300.ms))
        }
        hover {
            Modifier
                .backgroundColor(Color.rgba(0, 0, 0, 0.05F))
                .border(1.px, LineStyle.Solid, Color.rgba(0, 140, 160, 0.3F))
        }
    }

val ProjectCardStyleDark =
    CssStyle {
        base {
            Modifier
                .fillMaxWidth()
                .padding(32.px)
                .borderRadius(16.px)
                .backgroundColor(Color.rgba(255, 255, 255, 0.03F))
                .border(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.06F))
                .margin(bottom = 24.px)
                .transition(Transition.of("all", 300.ms))
        }
        hover {
            Modifier
                .backgroundColor(Color.rgba(255, 255, 255, 0.05F))
                .border(1.px, LineStyle.Solid, Color.rgba(100, 200, 220, 0.2F))
        }
    }

val ProjectTitleStyleLight =
    CssStyle {
        base {
            Modifier
                .color(Color.rgb(20, 20, 20))
                .fontWeight(FontWeight.Bold)
                .margin(bottom = 4.px, top = 0.px)
                .fontSize(1.5.cssRem)
                .letterSpacing((-0.02).em)
        }
    }

val ProjectTitleStyleDark =
    CssStyle {
        base {
            Modifier
                .color(Colors.White)
                .fontWeight(FontWeight.Bold)
                .margin(bottom = 4.px, top = 0.px)
                .fontSize(1.5.cssRem)
                .letterSpacing((-0.02).em)
        }
    }

val ProjectPositionStyleLight =
    CssStyle {
        base {
            Modifier
                .color(accentColorLight)
                .fontSize(0.85.cssRem)
                .fontWeight(FontWeight.Medium)
                .margin(bottom = 12.px, top = 0.px)
        }
    }

val ProjectPositionStyleDark =
    CssStyle {
        base {
            Modifier
                .color(accentColorDark)
                .fontSize(0.85.cssRem)
                .fontWeight(FontWeight.Medium)
                .margin(bottom = 12.px, top = 0.px)
        }
    }

val ProjectDescriptionStyleLight =
    CssStyle {
        base {
            Modifier
                .color(Color.rgba(0, 0, 0, 0.6F))
                .fontSize(0.95.cssRem)
                .lineHeight(1.7)
                .margin(bottom = 24.px, top = 0.px)
        }
    }

val ProjectDescriptionStyleDark =
    CssStyle {
        base {
            Modifier
                .color(Color.rgba(255, 255, 255, 0.55F))
                .fontSize(0.95.cssRem)
                .lineHeight(1.7)
                .margin(bottom = 24.px, top = 0.px)
        }
    }

val PartListItemStyleLight =
    CssStyle {
        base {
            Modifier
                .fillMaxWidth()
                .padding(16.px, 0.px)
                .borderBottom(1.px, LineStyle.Solid, Color.rgba(0, 0, 0, 0.08F))
        }
    }

val PartListItemStyleDark =
    CssStyle {
        base {
            Modifier
                .fillMaxWidth()
                .padding(16.px, 0.px)
                .borderBottom(1.px, LineStyle.Solid, Color.rgba(255, 255, 255, 0.06F))
        }
    }

val PartListTitleStyleLight =
    CssStyle {
        base {
            Modifier
                .color(Color.rgb(20, 20, 20))
                .fontSize(0.9.cssRem)
                .fontWeight(FontWeight.Medium)
                .margin(bottom = 6.px)
        }
    }

val PartListTitleStyleDark =
    CssStyle {
        base {
            Modifier
                .color(Colors.White)
                .fontSize(0.9.cssRem)
                .fontWeight(FontWeight.Medium)
                .margin(bottom = 6.px)
        }
    }

@Composable
fun IndexProjects() {
    val colorMode = ColorMode.current
    val sectionHeaderStyle = if (colorMode.isLight) ProjectsSectionHeaderStyleLight else ProjectsSectionHeaderStyleDark

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
            H2(attrs = sectionHeaderStyle.toAttrs()) {
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
    val colorMode = ColorMode.current
    val projectCardStyle = if (colorMode.isLight) ProjectCardStyleLight else ProjectCardStyleDark
    val projectTitleStyle = if (colorMode.isLight) ProjectTitleStyleLight else ProjectTitleStyleDark
    val projectPositionStyle = if (colorMode.isLight) ProjectPositionStyleLight else ProjectPositionStyleDark
    val projectDescriptionStyle = if (colorMode.isLight) ProjectDescriptionStyleLight else ProjectDescriptionStyleDark

    Column(
        modifier = projectCardStyle.toModifier(),
    ) {
        H3(attrs = projectTitleStyle.toAttrs()) {
            Text(project.name)
        }
        project.position?.let { position ->
            P(attrs = projectPositionStyle.toAttrs()) {
                Text(position)
            }
        }
        P(attrs = projectDescriptionStyle.toAttrs()) {
            Text(project.description)
        }

        PartsListStyle(project.parts)
    }
}

@Composable
fun PartsListStyle(parts: List<ProjectPart>) {
    val colorMode = ColorMode.current
    val partListItemStyle = if (colorMode.isLight) PartListItemStyleLight else PartListItemStyleDark
    val partListTitleStyle = if (colorMode.isLight) PartListTitleStyleLight else PartListTitleStyleDark
    val techStackTextColor = if (colorMode.isLight) Color.rgba(0, 0, 0, 0.55F) else Color.rgba(255, 255, 255, 0.5F)
    val linkAccentColor = if (colorMode.isLight) accentColorLight else accentColorDark

    Column(modifier = Modifier.fillMaxWidth()) {
        for (part in parts) {
            Column(modifier = partListItemStyle.toModifier()) {
                Span(attrs = partListTitleStyle.toAttrs()) {
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
                                    Box(modifier = Modifier.margin(right = 6.px).opacity(0.6F)) {
                                        DevIcon(icon = techStack.icon)
                                    }
                                    Span {
                                        SpanText(
                                            text = techStack.name,
                                            modifier =
                                                Modifier
                                                    .color(techStackTextColor)
                                                    .fontSize(0.8.cssRem),
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        for (url in part.urls) {
                            Link(
                                path = url.url,
                                modifier =
                                    Modifier
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
