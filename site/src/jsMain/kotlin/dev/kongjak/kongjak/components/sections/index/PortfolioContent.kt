package dev.kongjak.kongjak.components.sections.index

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kongjak.kongjak.utils.BLOG_URL
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.B
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

private data class ImplementationNote(
    val id: String,
    val title: String,
    val summary: String,
    val meta: String,
    val detail: String,
)

private data class WorkItem(
    val title: String,
    val role: String,
    val period: String,
    val description: String,
    val stack: String,
    val href: String,
)

private data class ExperienceItem(val period: String, val title: String, val organization: String)

private data class OpenSourceItem(
    val repository: String,
    val contribution: String,
    val href: String,
    val pullRequest: String,
)

private val implementationNotes = listOf(
    ImplementationNote("01", "Compose HTML 렌더러", "XML 이벤트 파서로 HTML과 CSS를 읽어 AnnotatedString으로 변환하고 이미지와 테이블을 함께 렌더링했습니다.", "Koreatech Board · Kotlin", "공지 본문의 텍스트와 스타일은 AnnotatedString으로 변환했습니다. 이미지와 테이블은 파싱 순서를 유지한 채 별도 큐에 저장해 텍스트 사이에 배치했고, 이미지는 Coil, 테이블은 WebView로 렌더링했습니다. 파싱 작업은 비동기로 처리하고 색상은 luminance를 기준으로 다크 모드에 맞게 변환했습니다."),
    ImplementationNote("02", "공지 알림과 오프라인 저장", "FCM Topic으로 게시판을 구독하고 알림으로 받은 글을 Room에 저장해 오프라인에서도 볼 수 있게 했습니다.", "Koreatech Board · FCM · Room", "사용자가 선택한 학부와 학과 게시판을 FCM Topic으로 구독하도록 구성했습니다. 새 공지 알림에서 게시글 ID를 받은 뒤 API를 호출해 본문을 Room에 저장했습니다. 알림으로 받은 글은 네트워크 연결이 없어도 다시 열 수 있습니다."),
    ImplementationNote("03", "Android 9–11 포팅", "공식 지원이 끝난 Vega Pop-Up Note의 디바이스 트리와 커널을 관리하고 LineageOS를 빌드해 배포했습니다.", "LineageOS · AOSP · Linux", "Pantech Vega Pop-Up Note에 맞는 device tree, vendor 설정과 kernel source를 관리했습니다. Android 버전이 올라갈 때 변경된 HAL과 빌드 설정을 기기에 맞게 수정하고, LineageOS 16부터 18.1 기반의 빌드를 배포했습니다."),
    ImplementationNote("04", "오픈소스 버그 수정", "Tempus의 장르 검색 오류와 Tailscale Android의 navigation bar inset 누락을 수정해 업스트림에 반영했습니다.", "Tempus · Tailscale Android", "Tempus에서는 장르 검색 시 잘못된 조건으로 결과가 나오지 않던 문제를 수정하고 한국어 번역을 갱신했습니다. Tailscale Android에서는 peer 목록 하단이 navigation bar와 겹치는 문제를 찾아 inset 처리를 추가했습니다. 세 변경 모두 업스트림에 병합됐습니다."),
    ImplementationNote("05", "Result 기반 에러 처리", "서버 에러 코드를 Exception으로 변환하고, 반복되던 분기 처리를 Kotlin DSL로 정리했습니다.", "KOIN · Architecture · Kotlin DSL", "Repository에서 HTTP 상태 코드와 서버 에러 코드를 커스텀 Exception으로 변환해 ViewModel로 전달하도록 책임을 나눴습니다. 단일 상태 코드, 상태 코드 범위, 서버 에러 코드 조합을 같은 DSL에서 처리할 수 있도록 만들었고 기존 중복 분기를 줄였습니다."),
)

private val selectedWork = listOf(
    WorkItem("KOIN", "Android Developer · Project Lead", "2024 — NOW", "DAU 1,100+ 캠퍼스 커뮤니티. 가입 흐름을 재설계해 전환율을 높였고, 앱 아키텍처와 개발 프로세스를 함께 개선합니다.", "Kotlin · Compose · MVI", "https://github.com/BCSDLab/KOIN_ANDROID"),
    WorkItem("Koreatech Board", "Independent Developer", "2022 — 2024", "공지 수집부터 Go API, Android 클라이언트까지 혼자 구축했습니다. FCM과 Room 캐싱, 커스텀 HTML 렌더러를 구현했습니다.", "Kotlin · Go · Python", "https://github.com/kongwoojin/koreatech-board-android"),
    WorkItem("LineageOS for EF65", "Device Maintainer", "2019 — 2022", "공식 지원이 끝난 Pantech Vega Pop-Up Note에 Android 9부터 11까지 포팅하고 배포했습니다.", "AOSP · C++ · Linux", "https://github.com/sky-vega-dev-team"),
)

private val experience = listOf(
    ExperienceItem("2026 — NOW", "KOIN Project Lead", "BCSD Lab"),
    ExperienceItem("2024 — 2025", "Android Track Lead", "BCSD Lab"),
    ExperienceItem("2025", "Android Education Lead", "BCSD Lab"),
    ExperienceItem("2024 — NOW", "KOIN Android Developer", "BCSD Lab"),
    ExperienceItem("2022 — 2023", "JJBAKSA Android Developer", "BCSD Lab"),
    ExperienceItem("2022 — NOW", "Android Developer", "BCSD Lab"),
    ExperienceItem("2019 — 2022", "Vega Pop-Up Note Maintainer", "SKY VEGA DEV TEAM"),
)

private val openSourceContributions = listOf(
    OpenSourceItem("Tailscale Android", "Navigation bar inset 누락 수정", "https://github.com/tailscale/tailscale-android/pull/719", "PR #719"),
    OpenSourceItem("Tempus", "장르 검색 오류 수정 · 한국어 번역 개선", "https://github.com/eddyizm/tempus/pull/817", "2 PRs"),
    OpenSourceItem("NEIS API", "학사일정 조회 오류 수정", "https://github.com/agemor/neis-api/pull/12", "PR #12"),
)

@Composable
fun IndexEngineering() {
    Section(attrs = { id("engineering") }) {
        Div(attrs = { classes("section-intro", "compact") }) {
            Span { Text("01 · IMPLEMENTATION NOTES") }
            H2 { Text("프로젝트에서 구현한 내용") }
        }
        Div(attrs = { classes("engineering-list") }) {
            implementationNotes.forEach { note -> key(note.id) { EngineeringItem(note) } }
        }
    }
}

@Composable
private fun EngineeringItem(note: ImplementationNote) {
    var open by remember { mutableStateOf(false) }
    Button(attrs = {
        classes("engineering-item")
        attr("aria-expanded", open.toString())
        attr("data-open", open.toString())
        onClick { open = !open }
    }) {
        Span(attrs = { classes("eng-index") }) { Text(note.id) }
        Div { H3 { Text(note.title) }; P { Text(note.summary) } }
        Div(attrs = { classes("eng-side") }) {
            Span(attrs = { classes("eng-meta") }) { Text(note.meta) }
            Span(attrs = { classes("eng-toggle"); attr("aria-hidden", "true") }) { Text(if (open) "−" else "+") }
        }
        Div(attrs = { classes("eng-detail") }) { P { Text(note.detail) } }
    }
}

@Composable
fun IndexSelectedWork() {
    Section(attrs = { id("projects") }) {
        Div(attrs = { classes("section-intro", "compact") }) {
            Span { Text("02 · SELECTED WORK") }
            H2 { Text("지금까지 만든 프로젝트") }
        }
        Div(attrs = { classes("work-grid") }) {
            selectedWork.forEach { work -> key(work.title) { Work(work) } }
        }
    }
}

@Composable
private fun Work(work: WorkItem) {
    A(href = work.href, attrs = { classes("work-card"); attr("target", "_blank"); attr("rel", "noopener") }) {
        Div(attrs = { classes("work-top") }) { Span { Text(work.role) }; Span { Text(work.period) } }
        H3 { Text(work.title) }
        P { Text(work.description) }
        Div(attrs = { classes("work-bottom") }) { Span { Text(work.stack) }; B { Text("View repository ↗") } }
    }
}

@Composable
fun IndexExperience() {
    Section(attrs = { id("experience") }) {
        Div(attrs = { classes("section-intro", "compact") }) {
            Span { Text("03 · EXPERIENCE") }
            H2 { Text("활동과 경험") }
        }
        Div(attrs = { classes("experience-grid") }) {
            Div(attrs = { classes("timeline") }) {
                experience.forEach { item -> Timeline(item) }
            }
            Div(attrs = { classes("profile-notes") }) {
                Span(attrs = { classes("oss-kicker") }) { Text("MERGED UPSTREAM") }
                H3 { Text("Open source contributions") }
                Div(attrs = { classes("oss-list") }) {
                    openSourceContributions.forEach { item -> OssEntry(item) }
                }
                P(attrs = { classes("background-note") }) { Text("한국기술교육대학교 컴퓨터공학부 재학 · AOSP 디바이스 트리와 Linux 작업 · 리눅스마스터 2급 · 정보처리기능사") }
                Div(attrs = { classes("stack-cloud") }) {
                    listOf("Kotlin", "Jetpack Compose", "MVI", "Clean Architecture", "Retrofit", "Room", "Maestro", "GitHub Actions", "Linux").forEach { Span { Text(it) } }
                }
            }
        }
    }
}

@Composable
private fun OssEntry(item: OpenSourceItem) {
    A(href = item.href, attrs = { classes("oss-entry"); attr("target", "_blank"); attr("rel", "noopener") }) {
        Div { B { Text(item.repository) }; Span { Text(item.contribution) } }
        Span(attrs = { classes("oss-pr") }) { Text("${item.pullRequest} ↗") }
    }
}

@Composable
private fun Timeline(item: ExperienceItem) {
    Div(attrs = { classes("timeline-row") }) {
        Span { Text(item.period) }
        Div { B { Text(item.title) }; Span { Text(item.organization) } }
    }
}

@Composable
fun IndexContact() {
    Section(attrs = { id("contact"); classes("contact-new") }) {
        Span(attrs = { classes("contact-label") }) { Text("CONTACT") }
        H2 { Text("연락은 이메일로 주세요.") }
        P { Text("Android 개발과 관련된 문의는 아래 주소로 받을 수 있습니다.") }
        Div(attrs = { classes("contact-links") }) {
            A(href = "mailto:kongjak@kongjak.dev") { Text("kongjak@kongjak.dev ↗") }
            A(href = "https://github.com/kongwoojin", attrs = { attr("target", "_blank"); attr("rel", "noopener") }) { Text("GitHub ↗") }
            A(href = "/resume.pdf", attrs = { attr("target", "_blank") }) { Text("Resume ↗") }
            A(href = BLOG_URL, attrs = { attr("target", "_blank"); attr("rel", "noopener") }) { Text("Blog ↗") }
        }
    }
}
