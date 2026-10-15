package dev.kongjak.kongjak.components.sections.index

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.*

@Composable
fun IndexSkills() {
    Section(attrs = { id("skills"); classes("portfolio-section"); attr("aria-labelledby", "skills-title") }) {
        SectionHeading("기술 스택", "skills-title", "주로 사용하는 기술과 도구입니다.")
        Ul(attrs = { classes("skill-tags") }) {
            listOf(
                "Kotlin", "Jetpack Compose", "Java", "Room", "Retrofit",
                "MVI", "Clean Architecture", "Coroutines", "Maestro", "GitHub Actions",
                "Go", "Python", "FastAPI", "Linux", "Git"
            ).forEach { skill ->
                Li(attrs = { classes("skill-chip") }) { Text(skill) }
            }
        }
    }
}
