package com.kongjak.kongjak.utils

import com.kongjak.kongjak.models.FaIcons.*
import com.kongjak.kongjak.models.Project
import com.kongjak.kongjak.models.ProjectPart
import com.kongjak.kongjak.models.ProjectUrl
import com.kongjak.kongjak.models.Skill
import com.kongjak.kongjak.models.TechStack
import com.kongjak.kongjak.utils.DevIcons.KOTLIN

const val GITHUB_URL = "https://github.com/kongwoojin/"
const val TELEGRAM_URL = "https://t.me/Kongjak"
const val MAIL_ADDRESS = "mailto:kongjak@kongjak.dev"
const val BLOG_URL = "https://blog.kongjak.dev"

private const val SIMPLE_ICONS_CDN = "https://cdn.simpleicons.org"

val languages =
    listOf(
        Skill("Kotlin", "$SIMPLE_ICONS_CDN/kotlin/FFFFFF"),
        Skill("Python", "$SIMPLE_ICONS_CDN/python/FFFFFF"),
        Skill("Java", "$SIMPLE_ICONS_CDN/openjdk/FFFFFF"),
    )

val tools =
    listOf(
        Skill("IntelliJ IDEA", "$SIMPLE_ICONS_CDN/intellijidea/FFFFFF"),
        Skill("Android Studio", "$SIMPLE_ICONS_CDN/androidstudio/FFFFFF"),
        Skill("PyCharm", "$SIMPLE_ICONS_CDN/pycharm/FFFFFF"),
        Skill("Git", "$SIMPLE_ICONS_CDN/git/FFFFFF"),
    )

val platformsAndFrameworks =
    listOf(
        Skill("Android", "$SIMPLE_ICONS_CDN/android/FFFFFF"),
        Skill("Jetpack Compose", "$SIMPLE_ICONS_CDN/jetpackcompose/FFFFFF"),
        Skill("Arduino", "$SIMPLE_ICONS_CDN/arduino/FFFFFF"),
        Skill("FastAPI", "$SIMPLE_ICONS_CDN/fastapi/FFFFFF"),
        Skill("Linux", "$SIMPLE_ICONS_CDN/linux/FFFFFF"),
        Skill("Nginx", "$SIMPLE_ICONS_CDN/nginx/FFFFFF"),
    )

val projectLists =
    listOf(
        Project(
            name = "KOIN",
            description = "한국기술교육대학교 재학생 60% 이상이 이용하는 캠퍼스 생활 필수 앱입니다. 학식, 버스, 주변 식당, 시간표 등 다양한 서비스를 Android, iOS, Web에서 제공하며, 매일 1,100명 이상의 학생들이 사용하고 있습니다.",
            position = "Android Developer @ BCSD",
            parts =
                listOf(
                    ProjectPart(
                        name = "Android",
                        urls =
                            listOf(
                                ProjectUrl(
                                    url = "https://play.google.com/store/apps/details?id=in.koreatech.koin",
                                    urlName = "Play Store",
                                    icon = ANDROID,
                                ),
                                ProjectUrl(
                                    url = "https://github.com/BCSDLab/KOIN_ANDROID",
                                    urlName = "Github",
                                    icon = GITHUB,
                                ),
                            ),
                        techStacks =
                            listOf(
                                TechStack("Kotlin", KOTLIN),
                                TechStack("Jetpack Compose", DevIcons.JETPACK_COMPOSE),
                            ),
                    ),
                    ProjectPart(
                        name = "iOS",
                        urls =
                            listOf(
                                ProjectUrl(
                                    url = "https://github.com/BCSDLab/KOIN_iOS",
                                    urlName = "Github",
                                    icon = GITHUB,
                                ),
                            ),
                        techStacks =
                            listOf(
                                TechStack("Swift", DevIcons.SWIFT),
                            ),
                    ),
                    ProjectPart(
                        name = "Backend",
                        urls =
                            listOf(
                                ProjectUrl(
                                    url = "https://github.com/BCSDLab/KOIN_API_V2",
                                    urlName = "Github",
                                    icon = GITHUB,
                                ),
                            ),
                        techStacks =
                            listOf(
                                TechStack("Java", DevIcons.JAVA),
                                TechStack("Spring", DevIcons.SPRING),
                            ),
                    ),
                    ProjectPart(
                        name = "Frontend",
                        urls =
                            listOf(
                                ProjectUrl(
                                    url = "https://koreatech.in",
                                    urlName = "Website",
                                    icon = DOWNLOAD,
                                ),
                                ProjectUrl(
                                    url = "https://github.com/BCSDLab/KOIN_WEB_RECODE",
                                    urlName = "Github",
                                    icon = GITHUB,
                                ),
                            ),
                        techStacks =
                            listOf(
                                TechStack("React", DevIcons.REACT),
                                TechStack("TypeScript", DevIcons.TYPESCRIPT),
                            ),
                    ),
                ),
        ),
        Project(
            name = "Koreatech Board",
            description = "학교 게시판을 모바일에서 편하게 확인할 수 있는 앱입니다. Android 앱, Go API, Python 크롤러로 구성되어 게시판 내용을 실시간으로 동기화합니다.",
            position = "Developer",
            parts =
                listOf(
                    ProjectPart(
                        name = "Android",
                        urls =
                            listOf(
                                ProjectUrl("#", "App", ANDROID),
                                ProjectUrl("https://github.com/kongwoojin/koreatech-board-android", "Github", GITHUB),
                            ),
                        techStacks =
                            listOf(
                                TechStack("Kotlin", KOTLIN),
                                TechStack("Jetpack Compose", DevIcons.JETPACK_COMPOSE),
                            ),
                    ),
                    ProjectPart(
                        name = "API",
                        urls =
                            listOf(
                                ProjectUrl("https://github.com/kongwoojin/koreatech-board-api", "Github", GITHUB),
                            ),
                        techStacks =
                            listOf(
                                TechStack("Go", DevIcons.GO),
                            ),
                    ),
                    ProjectPart(
                        name = "Crawler",
                        urls =
                            listOf(
                                ProjectUrl("https://github.com/kongwoojin/koreatech-board-crawler", "Github", GITHUB),
                            ),
                        techStacks =
                            listOf(
                                TechStack("Python", DevIcons.PYTHON),
                            ),
                    ),
                ),
        ),
        Project(
            name = "gobuild",
            description = "Go 애플리케이션을 여러 OS와 아키텍처로 한 번에 빌드할 수 있는 CLI 도구입니다. 크로스 컴파일 과정을 자동화하여 배포를 간편하게 만들어줍니다.",
            position = "Developer",
            parts =
                listOf(
                    ProjectPart(
                        name = "CLI",
                        urls =
                            listOf(
                                ProjectUrl("https://github.com/kongwoojin/gobuild", "Github", GITHUB),
                            ),
                        techStacks =
                            listOf(
                                TechStack("Go", DevIcons.GO),
                            ),
                    ),
                ),
        ),
        Project(
            name = "LineageOS for EF65S",
            description = "팬택 베가 팝업노트를 위한 커스텀 ROM입니다. 공식 지원이 끝난 기기에 Android 9, 10을 설치할 수 있게 해주며, 최신 보안 패치도 함께 제공합니다.",
            position = "Maintainer @ SKY VEGA DEV TEAM",
            parts =
                listOf(
                    ProjectPart(
                        name = "ROM",
                        urls =
                            listOf(
                                ProjectUrl("https://dl.kongjak.dev/ef65/LineageOS/", "Download", DOWNLOAD),
                                ProjectUrl("https://github.com/sky-vega-dev-team", "Github", GITHUB),
                            ),
                    ),
                ),
        ),
        Project(
            name = "Pantech Archive",
            description = "팬택 기기의 펌웨어와 오픈소스 자료를 보존하는 아카이브입니다. 더 이상 공식 지원을 받지 못하는 기기들을 위한 자료를 제공합니다.",
            position = "Developer",
            parts =
                listOf(
                    ProjectPart(
                        name = "Website",
                        urls =
                            listOf(
                                ProjectUrl("https://pantech.kongjak.dev", "Visit", DOWNLOAD),
                            ),
                    ),
                ),
        ),
        Project(
            name = "More Projects",
            description = "GitHub에서 더 많은 오픈소스 프로젝트를 확인해보세요.",
            parts =
                listOf(
                    ProjectPart(
                        name = "Github",
                        urls =
                            listOf(
                                ProjectUrl("https://github.com/kongwoojin", "Github", GITHUB),
                            ),
                    ),
                ),
        ),
    )
