package com.kongjak.kongjak.components.sections.index

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import com.varabyte.kobweb.silk.components.icons.fa.FaChevronRight
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Article
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

private data class ProjectModule(
    val name: String,
    val tags: List<String>,
    val links: List<Pair<String, String>>,
)

private data class PortfolioProject(
    val index: String,
    val title: String,
    val role: String,
    val roleAt: String? = null,
    val description: String,
    val modules: List<ProjectModule>,
)

private val projects = listOf(
    PortfolioProject(
        index = "01",
        title = "KOIN",
        role = "Android Developer",
        roleAt = "BCSD",
        description = "DAU 1,100+ 의 한국기술교육대학교 커뮤니티 서비스입니다. Android, iOS, Frontend, Backend, Design, Product Manager 및 Data Analyst 직군이 참여하고 있으며, 학식, 버스, 주변 식당, 시간표 등 기능을 Android, iOS, Web에서 제공합니다.",
        modules = listOf(
            ProjectModule(
                name = "Android",
                tags = listOf("Kotlin", "Jetpack Compose"),
                links = listOf(
                    "Play Store" to "https://play.google.com/store/apps/details?id=in.koreatech.koin",
                    "Github" to "https://github.com/BCSDLab/KOIN_ANDROID",
                ),
            ),
        ),
    ),
    PortfolioProject(
        index = "02",
        title = "Koreatech Board",
        role = "Developer",
        description = "학교 게시판을 모바일에서 편하게 확인할 수 있는 앱입니다. Android 애플리케이션, Go API, Python 크롤러로 구성되어 게시판 내용을 실시간으로 동기화합니다.",
        modules = listOf(
            ProjectModule("Android", listOf("Kotlin", "Jetpack Compose"), listOf("Github" to "https://github.com/kongwoojin/koreatech-board-android")),
            ProjectModule("API", listOf("Go"), listOf("Github" to "https://github.com/kongwoojin/koreatech-board-api")),
            ProjectModule("Crawler", listOf("Python"), listOf("Github" to "https://github.com/kongwoojin/koreatech-board-crawler")),
        ),
    ),
    PortfolioProject(
        index = "03",
        title = "gobuild",
        role = "Developer",
        description = "Go 애플리케이션을 여러 OS와 아키텍처로 한 번에 빌드할 수 있는 CLI 도구입니다. 크로스 컴파일 과정을 자동화하여 배포를 간편하게 만들어줍니다.",
        modules = listOf(
            ProjectModule("CLI", listOf("Go"), listOf("Github" to "https://github.com/kongwoojin/gobuild")),
        ),
    ),
    PortfolioProject(
        index = "04",
        title = "LineageOS for EF65S",
        role = "Maintainer",
        roleAt = "SKY VEGA DEV TEAM",
        description = "팬택 베가 팝업노트를 위한 Custom ROM입니다. 공식 지원이 끝난 기기에 Android 9부터 11을 포팅하였습니다.",
        modules = listOf(
            ProjectModule("ROM", listOf("Android 9–11", "EF65S"), listOf(
                "Download" to "https://dl.kongjak.dev/ef65/LineageOS/",
                "Github" to "https://github.com/sky-vega-dev-team",
            )),
        ),
    ),
    PortfolioProject(
        index = "05",
        title = "Pantech Archive",
        role = "Developer",
        description = "팬택 기기의 펌웨어와 오픈소스 자료를 보존하는 아카이브입니다.",
        modules = listOf(
            ProjectModule("Website", listOf("Archive"), listOf("Visit" to "https://pantech.kongjak.dev")),
        ),
    ),
)

@Composable
fun IndexProjects() {
    val openState = remember { mutableStateMapOf<Int, Boolean>().also { it[0] = true } }

    Section(attrs = { id("projects") }) {
        SectionHead("02", "projects/")

        Div(attrs = { classes("projects"); id("projectList") }) {
            projects.forEachIndexed { idx, project ->
                val isOpen = openState[idx] ?: false
                Article(attrs = {
                    classes("project", "reveal")
                    attr("data-open", if (isOpen) "true" else "false")
                }) {
                    Div(attrs = {
                        classes("p-head")
                        onClick { openState[idx] = !isOpen }
                    }) {
                        Span(attrs = { classes("p-idx", "mono") }) { Text(project.index) }
                        Div(attrs = { classes("p-title-wrap") }) {
                            Div(attrs = { classes("p-title") }) { Text(project.title) }
                            Div(attrs = { classes("p-role") }) {
                                Text(project.role)
                                if (project.roleAt != null) {
                                    Span(attrs = { classes("at") }) { Text(" @ ") }
                                    Text(project.roleAt)
                                }
                            }
                        }
                        Span(attrs = { classes("p-toggle"); attr("aria-hidden", "true") }) {
                            ChevronIcon()
                        }
                        Span {}
                    }
                    Div(attrs = { classes("p-body") }) {
                        Div {
                            Div(attrs = { classes("p-body-inner") }) {
                                P(attrs = { classes("p-desc") }) { Text(project.description) }
                                Div(attrs = { classes("p-modules") }) {
                                    for (module in project.modules) {
                                        Div(attrs = { classes("p-mod") }) {
                                            Div(attrs = { classes("p-mod-name") }) { Text(module.name) }
                                            Div(attrs = { classes("p-mod-tags") }) {
                                                for (tag in module.tags) {
                                                    Span(attrs = { classes("p-tag") }) { Text(tag) }
                                                }
                                            }
                                            Div(attrs = { classes("p-mod-links") }) {
                                                for ((label, url) in module.links) {
                                                    A(
                                                        href = url,
                                                        attrs = {
                                                            classes("p-link")
                                                            attr("target", "_blank")
                                                            attr("rel", "noopener")
                                                        }
                                                    ) { Text(label) }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        P(attrs = {
            classes("mono")
            style { property("margin-top", "18px"); property("font-size", "12px"); property("color", "var(--fg-mute)") }
        }) {
            Span(attrs = { style { property("color", "var(--fg-faint)") } }) { Text("$ ") }
            Text("ls ~/projects | wc -l  ")
            Span(attrs = { style { property("color", "var(--fg-faint)") } }) { Text("→ ") }
            A(
                href = "https://github.com/kongwoojin",
                attrs = {
                    attr("target", "_blank")
                    attr("rel", "noopener")
                    style { property("color", "var(--fg)"); property("border-bottom", "1px dashed var(--line-2)") }
                }
            ) { Text("github.com/kongwoojin") }
            Text(" 에서 더 많은 프로젝트를 확인해보세요.")
        }
    }
}

@Composable
private fun ChevronIcon() {
    FaChevronRight()
}
