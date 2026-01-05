package com.kongjak.kongjak.components.sections.index

import androidx.compose.runtime.Composable
import com.kongjak.kongjak.components.widgets.TextTooltip
import com.kongjak.kongjak.models.Skill
import com.kongjak.kongjak.utils.languages
import com.kongjak.kongjak.utils.platformsAndFrameworks
import com.kongjak.kongjak.utils.tools
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
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Text

private val accentColor = Color.rgb(100, 200, 220)

val SkillsSectionHeaderStyle =
    CssStyle {
        base {
            Modifier
                .fontSize(0.9.cssRem)
                .fontWeight(FontWeight.Bold)
                .color(accentColor)
                .letterSpacing(0.15.em)
                .margin(bottom = 64.px)
                .textAlign(TextAlign.Center)
        }
    }

val SkillCategoryTitleStyle =
    CssStyle {
        base {
            Modifier
                .fontSize(1.1.cssRem)
                .fontWeight(FontWeight.SemiBold)
                .color(Color.rgba(255, 255, 255, 0.4F))
                .letterSpacing(0.1.em)
                .margin(bottom = 24.px)
                .textAlign(TextAlign.Center)
        }
    }

val SkillIconStyle =
    CssStyle {
        base {
            Modifier
                .padding(16.px)
                .margin(8.px)
                .borderRadius(12.px)
                .opacity(0.7F)
                .transition(Transition.of("all", 200.ms))
        }
        hover {
            Modifier.opacity(1F)
        }
    }

@Composable
fun IndexSkills() {
    Column(
        modifier =
            Modifier
                .minWidth(100.percent)
                .minHeight(100.vh)
                .id("skills")
                .padding(topBottom = 100.px, leftRight = 24.px),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        H2(attrs = SkillsSectionHeaderStyle.toAttrs()) {
            Text("SKILLS")
        }

        Column(
            modifier = Modifier.fillMaxWidth().maxWidth(1000.px),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SimpleGrid(
                modifier = Modifier.fillMaxWidth(),
                numColumns = numColumns(1, md = 3),
            ) {
                SkillCategoryWidget("LANGUAGES", languages)
                SkillCategoryWidget("PLATFORMS & FRAMEWORKS", platformsAndFrameworks)
                SkillCategoryWidget("TOOLS", tools)
            }
        }
    }
}

@Composable
fun SkillCategoryWidget(
    title: String,
    skills: List<Skill>,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.px),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        H3(attrs = SkillCategoryTitleStyle.toAttrs()) {
            Text(title)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            SimpleGrid(
                numColumns = numColumns(3),
            ) {
                for (skill in skills) {
                    Box(modifier = SkillIconStyle.toModifier()) {
                        Image(
                            src = skill.iconPath,
                            alt = skill.name,
                            width = 40,
                            height = 40,
                        )
                    }
                    TextTooltip(text = skill.name)
                }
            }
        }
    }
}
