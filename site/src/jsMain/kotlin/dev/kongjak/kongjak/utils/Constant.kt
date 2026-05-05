package dev.kongjak.kongjak.utils

import dev.kongjak.kongjak.utils.DevIcons.KOTLIN
import dev.kongjak.kongjak.models.FaIcons
import dev.kongjak.kongjak.models.Project
import dev.kongjak.kongjak.models.ProjectPart
import dev.kongjak.kongjak.models.ProjectUrl
import dev.kongjak.kongjak.models.Skill
import dev.kongjak.kongjak.models.TechStack

const val GITHUB_URL = "https://github.com/kongwoojin/"
const val TELEGRAM_URL = "https://t.me/Kongjak"
const val MAIL_ADDRESS = "mailto:kongjak@kongjak.dev"
const val BLOG_URL = "https://blog.kongjak.dev"

val languages =
    listOf(
        Skill("Kotlin", "kotlin"),
        Skill("Python", "python"),
        Skill("Java", "openjdk"),
    )

val tools =
    listOf(
        Skill("Android Studio", "androidstudio"),
        Skill("IntelliJ IDEA", "intellijidea"),
        Skill("PyCharm", "pycharm"),
        Skill("Git", "git"),
    )

val platformsAndFrameworks =
    listOf(
        Skill("Android", "android"),
        Skill("Jetpack Compose", "jetpackcompose"),
        Skill("FastAPI", "fastapi"),
        Skill("Linux", "linux"),
        Skill("Nginx", "nginx"),
    )

val projectLists =
    listOf(
        Project(
            name = "KOIN",
            description =
                "DAU 1100+ 의 한국기술교육대학교 커뮤니티 서비스입니다. " +
                        "Android, iOS, Frontend, Backend, Design, Product Manager 및 Data Analyst 직군이 참여하고 있으며, 학식, 버스, 주변 식당, 시간표 등 기능을 Android, iOS, Web에서 제공합니다.",
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
                                    icon = FaIcons.ANDROID,
                                ),
                                ProjectUrl(
                                    url = "https://github.com/BCSDLab/KOIN_ANDROID",
                                    urlName = "Github",
                                    icon = FaIcons.GITHUB,
                                ),
                            ),
                        techStacks =
                            listOf(
                                TechStack(
                                    "Kotlin",
                                    KOTLIN
                                ),
                                TechStack(
                                    "Jetpack Compose",
                                    DevIcons.JETPACK_COMPOSE
                                ),
                            ),
                    ),
                    ProjectPart(
                        name = "iOS",
                        urls =
                            listOf(
                                ProjectUrl(
                                    url = "https://github.com/BCSDLab/KOIN_iOS",
                                    urlName = "Github",
                                    icon = FaIcons.GITHUB,
                                ),
                            ),
                        techStacks =
                            listOf(
                                TechStack(
                                    "Swift",
                                    DevIcons.SWIFT
                                ),
                            ),
                    ),
                    ProjectPart(
                        name = "Backend",
                        urls =
                            listOf(
                                ProjectUrl(
                                    url = "https://github.com/BCSDLab/KOIN_API_V2",
                                    urlName = "Github",
                                    icon = FaIcons.GITHUB,
                                ),
                            ),
                        techStacks =
                            listOf(
                                TechStack(
                                    "Java",
                                    DevIcons.JAVA
                                ),
                                TechStack(
                                    "Spring",
                                    DevIcons.SPRING
                                ),
                            ),
                    ),
                    ProjectPart(
                        name = "Frontend",
                        urls =
                            listOf(
                                ProjectUrl(
                                    url = "https://koreatech.in",
                                    urlName = "Website",
                                    icon = FaIcons.DOWNLOAD,
                                ),
                                ProjectUrl(
                                    url = "https://github.com/BCSDLab/KOIN_WEB_RECODE",
                                    urlName = "Github",
                                    icon = FaIcons.GITHUB,
                                ),
                            ),
                        techStacks =
                            listOf(
                                TechStack(
                                    "React",
                                    DevIcons.REACT
                                ),
                                TechStack(
                                    "TypeScript",
                                    DevIcons.TYPESCRIPT
                                ),
                            ),
                    ),
                ),
        ),
        Project(
            name = "Koreatech Board",
            description = "학교 게시판을 모바일에서 편하게 확인할 수 있는 앱입니다. Android 애플리케이션, Go API, Python 크롤러로 구성되어 게시판 내용을 실시간으로 동기화합니다.",
            position = "Developer",
            parts =
                listOf(
                    ProjectPart(
                        name = "Android",
                        urls =
                            listOf(
                                ProjectUrl(
                                    "https://github.com/kongwoojin/koreatech-board-android", "Github",
                                    FaIcons.GITHUB
                                ),
                            ),
                        techStacks =
                            listOf(
                                TechStack(
                                    "Kotlin",
                                    KOTLIN
                                ),
                                TechStack(
                                    "Jetpack Compose",
                                    DevIcons.JETPACK_COMPOSE
                                ),
                            ),
                    ),
                    ProjectPart(
                        name = "API",
                        urls =
                            listOf(
                                ProjectUrl(
                                    "https://github.com/kongwoojin/koreatech-board-api", "Github",
                                    FaIcons.GITHUB
                                ),
                            ),
                        techStacks =
                            listOf(
                                TechStack(
                                    "Go",
                                    DevIcons.GO
                                ),
                            ),
                    ),
                    ProjectPart(
                        name = "Crawler",
                        urls =
                            listOf(
                                ProjectUrl(
                                    "https://github.com/kongwoojin/koreatech-board-crawler", "Github",
                                    FaIcons.GITHUB
                                ),
                            ),
                        techStacks =
                            listOf(
                                TechStack(
                                    "Python",
                                    DevIcons.PYTHON
                                ),
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
                                ProjectUrl(
                                    "https://github.com/kongwoojin/gobuild",
                                    "Github",
                                    FaIcons.GITHUB
                                ),
                            ),
                        techStacks =
                            listOf(
                                TechStack(
                                    "Go",
                                    DevIcons.GO
                                ),
                            ),
                    ),
                ),
        ),
        Project(
            name = "LineageOS for EF65S",
            description = "팬택 베가 팝업노트를 위한 Custom Rom입니다. 공식 지원이 끝난 기기에 Android 9부터 11을 포팅하였습니다.",
            position = "Maintainer @ SKY VEGA DEV TEAM",
            parts =
                listOf(
                    ProjectPart(
                        name = "ROM",
                        urls =
                            listOf(
                                ProjectUrl(
                                    "https://dl.kongjak.dev/ef65/LineageOS/",
                                    "Download",
                                    FaIcons.DOWNLOAD
                                ),
                                ProjectUrl(
                                    "https://github.com/sky-vega-dev-team",
                                    "Github",
                                    FaIcons.GITHUB
                                ),
                            ),
                    ),
                ),
        ),
        Project(
            name = "Pantech Archive",
            description = "팬택 기기의 펌웨어와 오픈소스 자료를 보존하는 아카이브입니다.",
            position = "Developer",
            parts =
                listOf(
                    ProjectPart(
                        name = "Website",
                        urls =
                            listOf(
                                ProjectUrl(
                                    "https://pantech.kongjak.dev",
                                    "Visit",
                                    FaIcons.DOWNLOAD
                                ),
                            ),
                    ),
                ),
        ),
        Project(
            name = "More Projects",
            description = "GitHub에서 더 많은 프로젝트를 확인해보세요.",
            parts =
                listOf(
                    ProjectPart(
                        name = "Github",
                        urls =
                            listOf(
                                ProjectUrl(
                                    "https://github.com/kongwoojin",
                                    "Github",
                                    FaIcons.GITHUB
                                ),
                            ),
                    ),
                ),
        ),
    )
