package dev.kongjak.kongjak.components.sections.index

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul

@Composable
fun IndexSkills() {
    Section(attrs = { id("skills") }) {
        SectionHead("01", "skills.kt")

        Div(attrs = { classes("skills-grid") }) {
            SkillGroup("languages", listOf(
                "Kotlin" to "primary",
                "Python" to "tooling",
                "Java" to "legacy",
                "Go" to "cli",
            ))
            SkillGroup("platforms / frameworks", listOf(
                "Android" to "OS",
                "Jetpack Compose" to "UI",
                "FastAPI" to "api",
                "Linux · Nginx" to "ops",
            ))
            SkillGroup("tools", listOf(
                "Android Studio" to "IDE",
                "IntelliJ IDEA" to "IDE",
                "PyCharm" to "IDE",
                "Git" to "vcs",
            ))
        }
    }
}

@Composable
private fun SkillGroup(title: String, skills: List<Pair<String, String>>) {
    Div(attrs = { classes("skill-group", "reveal") }) {
        H3 { Text(title) }
        Ul(attrs = { classes("skill-list") }) {
            for ((name, meta) in skills) {
                Li {
                    Text(name)
                    Span(attrs = { classes("meta") }) { Text(meta) }
                }
            }
        }
    }
}
