package dev.kongjak.kongjak.components.sections.index

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kongjak.kongjak.utils.GITHUB_URL
import dev.kongjak.kongjak.utils.BLOG_URL
import org.jetbrains.compose.web.dom.*

private data class WorkItem(
    val title: String,
    val category: String,
    val role: String,
    val description: String,
    val stack: List<String>,
    val href: String,
    val images: List<String> = emptyList(),
)

private val selectedWork = listOf(
    WorkItem("KOIN", "팀 프로젝트", "Team Lead · Android Developer",
        "학식, 버스, 시간표부터 주변 식당까지. 한국기술교육대학교 학생들의 일상을 돕는 캠퍼스 서비스입니다. Android 앱 개발과 구조 개선을 맡고 있습니다.",
        listOf("Kotlin", "Jetpack Compose", "MVI"), "https://github.com/BCSDLab/KOIN_ANDROID"),
    WorkItem("Koreatech Board", "개인 프로젝트", "Developer",
        "여러 곳에 흩어진 학교 공지를 한곳에서 확인하는 앱입니다. 공지 수집과 API를 직접 만들고, 알림과 오프라인 저장 기능을 구현했습니다.",
        listOf("Kotlin", "Jetpack Compose", "Go", "Python"), "https://github.com/kongwoojin/koreatech-board-android"),
    WorkItem("Local Balance Trip", "팀 프로젝트", "Android Developer",
        "성향에 맞는 여행지를 추천하고, 코스를 저장해 지도와 함께 여행할 수 있는 Android 앱입니다. 화면 복귀 시 데이터 갱신, 다국어 UI, 지도 기반 투어 흐름 등을 개선했습니다.",
        listOf("Kotlin", "Jetpack Compose", "MVI", "Hilt", "Ktor", "Naver Map"), "https://github.com/HD-GZ/LOCAL_BALANCE_TRIP_ANDROID"),
    WorkItem("LineageOS for EF65S", "오픈소스", "Device Maintainer",
        "공식 지원이 끝난 베가 팝업노트에 Android 9부터 11까지 포팅했습니다. 디바이스 트리와 커널을 관리하며 빌드를 배포했습니다.",
        listOf("AOSP", "C++", "Linux"), "https://github.com/sky-vega-dev-team"),
    WorkItem("gobuild", "개인 도구", "Developer",
        "Go 프로젝트를 여러 운영체제와 아키텍처로 한 번에 빌드하는 CLI입니다. 반복하던 크로스 컴파일 과정을 간단하게 정리했습니다.",
        listOf("Go", "CLI"), "https://github.com/kongwoojin/gobuild"),
)

internal data class BlogPost(
    val title: String,
    val category: String,
    val date: String,
    val href: String,
)

internal val recentBlogPosts = listOf(
    BlogPost("Offline-first: 네트워크 없이도 작동하는 앱 만들기", "개발 · 안드로이드", "2026.10.07", "$BLOG_URL/43"),
    BlogPost("난독화와 R8 Full mode 적용하기", "개발 · 안드로이드", "2026.09.13", "$BLOG_URL/42"),
    BlogPost("[CS 스터디] 7주차 HTTP & DNS", "공부 · CS 스터디", "2026.07.06", "$BLOG_URL/41"),
)

@Composable
internal fun SectionHeading(title: String, titleId: String, description: String?) {
    Div(attrs = { classes("section-heading") }) {
        H2(attrs = { id(titleId) }) { Text(title) }
        if (!description.isNullOrBlank()) P { Text(description) }
    }
}

@Composable
fun IndexSelectedWork() {
    Section(attrs = { id("projects"); classes("portfolio-section"); attr("aria-labelledby", "projects-title") }) {
        SectionHeading("프로젝트", "projects-title", "참여한 프로젝트와 개인 작업입니다.")
        Div(attrs = { classes("featured-work-grid") }) {
            FeatureProjectCard(selectedWork[0].copy(images = listOf(
                "/projects/koin-home.png", "/projects/koin-notices.png", "/projects/koin-article.png",
            )))
            FeatureProjectCard(selectedWork[1].copy(images = listOf(
                "/projects/board-home.png", "/projects/board-list.png", "/projects/board-article.png",
            )))
        }
        Div(attrs = { classes("work-grid") }) {
            selectedWork.drop(2).forEach { work ->
                ProjectCard(work)
            }
        }
        Div(attrs = { classes("section-end") }) {
            A(href = GITHUB_URL, attrs = { classes("quiet-link"); attr("target", "_blank"); attr("rel", "noopener noreferrer") }) { Text("GitHub에서 더 보기 ↗") }
        }
    }
}

@Composable
private fun FeatureProjectCard(work: WorkItem) {
    var activeSlide by remember(work.title) { mutableStateOf(0) }
    Article(attrs = { classes("featured-work-card") }) {
        Div(attrs = { classes("feature-visual") }) {
            Img(src = work.images[activeSlide], attrs = {
                attr("alt", "${work.title} 화면 ${activeSlide + 1}")
                attr("loading", "lazy")
                attr("decoding", "async")
                classes("feature-image")
            })
            Div(attrs = { classes("slider-controls") }) {
                Button(attrs = {
                    classes("slider-button")
                    attr("type", "button")
                    attr("aria-label", "이전 이미지")
                    onClick { activeSlide = (activeSlide - 1 + work.images.size) % work.images.size }
                }) { Text("←") }
                Span(attrs = { classes("slider-count"); attr("aria-live", "polite") }) {
                    Text("${activeSlide + 1} / ${work.images.size}")
                }
                Button(attrs = {
                    classes("slider-button")
                    attr("type", "button")
                    attr("aria-label", "다음 이미지")
                    onClick { activeSlide = (activeSlide + 1) % work.images.size }
                }) { Text("→") }
            }
        }
        Div(attrs = { classes("featured-work-copy") }) {
            P(attrs = { classes("work-category") }) { Text(work.category) }
            H3 { A(href = work.href, attrs = { attr("target", "_blank"); attr("rel", "noopener noreferrer") }) { Text(work.title) } }
            P(attrs = { classes("work-role") }) { Text(work.role) }
            P(attrs = { classes("work-description") }) { Text(work.description) }
            Ul(attrs = { classes("project-tags") }) { work.stack.forEach { Li(attrs = { classes("project-chip") }) { Text(it) } } }
        }
    }
}

@Composable
private fun ProjectCard(work: WorkItem) {
    Article(attrs = { classes("work-card") }) {
        Div(attrs = { classes("work-body") }) {
            P(attrs = { classes("work-category") }) { Text(work.category) }
            H3 { A(href = work.href, attrs = { attr("target", "_blank"); attr("rel", "noopener noreferrer") }) { Text(work.title) } }
            P(attrs = { classes("work-role") }) { Text(work.role) }
            P(attrs = { classes("work-description") }) { Text(work.description) }
            Ul(attrs = { classes("project-tags") }) { work.stack.forEach { Li(attrs = { classes("project-chip") }) { Text(it) } } }
        }
    }
}

@Composable
fun IndexExperience() {
    Section(attrs = { id("about"); classes("portfolio-section", "about-section"); attr("aria-labelledby", "about-title") }) {
        SectionHeading("소개", "about-title", null)
        Div(attrs = { classes("about-grid") }) {
            Div(attrs = { classes("about-copy") }) {
                H3 { Text("함께 고민하고,"); Br(); Text("먼저 움직입니다.") }
                P {
                    Text("한국기술교육대학교 컴퓨터공학부에 재학 중입니다. ")
                    A(href = "https://bcsdlab.com", attrs = { attr("target", "_blank"); attr("rel", "noopener noreferrer") }) { Text("BCSD Lab") }
                    Text("에서 서비스를 개발하고, Android 트랙과 교육을 이끌었습니다.")
                }
                P { Text("문제가 보이면 먼저 원인을 살피고 개선을 제안합니다. 팀원들과 생각을 나누며 함께 해결하는 과정을 중요하게 생각합니다.") }
            }
            Div(attrs = { classes("experience-list") }) {
                ExperienceRow("2026", "Android Developer", "Local Balance Trip")
                ExperienceRow("2026 — 현재", "KOIN Team Lead", "BCSD Lab")
                ExperienceRow("2024 — 현재", "Android Track Lead", "BCSD Lab")
                ExperienceRow("2025, 2026", "Android Education Lead", "BCSD Lab")
                ExperienceRow("2024 — 현재", "KOIN Android Developer", "BCSD Lab")
                ExperienceRow("2019 — 2022", "Vega Pop-Up Note Maintainer", "SKY VEGA DEV TEAM")
            }
        }
        Div(attrs = { classes("opensource-row") }) {
            Span(attrs = { classes("small-label") }) { Text("오픈소스 기여") }
            Div {
                ContributionLink("Tailscale Android", "피어 목록 내비게이션 바 inset 수정", "https://github.com/tailscale/tailscale-android/pull/719")
                ContributionLink("Tempus", "뒤로가기 처리 개선", "https://github.com/eddyizm/tempus/pull/887")
                ContributionLink("Tempus", "장르 검색 오류 수정", "https://github.com/eddyizm/tempus/pull/817")
                ContributionLink("Tempus", "한국어 번역 보완", "https://github.com/eddyizm/tempus/pull/97")
                ContributionLink("NEIS API", "학사 일정 조회 오류 수정", "https://github.com/agemor/neis-api/pull/12")
            }
        }
    }
}

@Composable
private fun ExperienceRow(period: String, role: String, organization: String) {
    Div(attrs = { classes("experience-row") }) {
        Span(attrs = { classes("experience-period") }) { Text(period) }
        Div {
            H3 { Text(role) }
            P {
                if (organization == "BCSD Lab") {
                    A(href = "https://bcsdlab.com", attrs = { attr("target", "_blank"); attr("rel", "noopener noreferrer") }) { Text(organization) }
                } else {
                    Text(organization)
                }
            }
        }
    }
}

@Composable
private fun ContributionLink(name: String, description: String, href: String) {
    A(href = href, attrs = { classes("contribution-link"); attr("target", "_blank"); attr("rel", "noopener noreferrer") }) {
        Span { Text(name) }; Span { Text(description) }; Span(attrs = { attr("aria-hidden", "true") }) { Text("↗") }
    }
}

@Composable
fun IndexBlog() {
    Section(attrs = { id("blog"); classes("portfolio-section", "blog-section"); attr("aria-labelledby", "blog-title") }) {
        SectionHeading("블로그", "blog-title", "최근 글")
        Div(attrs = { classes("blog-list") }) {
            recentBlogPosts.forEach { post ->
                A(href = post.href, attrs = {
                    classes("blog-entry")
                    attr("target", "_blank")
                    attr("rel", "noopener noreferrer")
                }) {
                    Div(attrs = { classes("blog-entry-copy") }) {
                        H3 { Text(post.title) }
                        P(attrs = { classes("blog-meta") }) { Text("${post.category}  ·  ${post.date}") }
                    }
                    Span(attrs = { classes("blog-entry-arrow"); attr("aria-hidden", "true") }) { Text("→") }
                }
            }
        }
        Div(attrs = { classes("section-end") }) {
            Span {}
            A(href = BLOG_URL, attrs = { classes("quiet-link"); attr("target", "_blank"); attr("rel", "noopener noreferrer") }) { Text("블로그에서 더 보기 →") }
        }
    }
}
