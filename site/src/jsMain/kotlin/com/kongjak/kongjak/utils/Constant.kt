package com.kongjak.kongjak.utils

import com.kongjak.kongjak.models.FaIcons.ANDROID
import com.kongjak.kongjak.models.FaIcons.DOWNLOAD
import com.kongjak.kongjak.models.FaIcons.GITHUB
import com.kongjak.kongjak.models.Project
import com.kongjak.kongjak.models.ProjectUrl
import com.kongjak.kongjak.models.Skill
import com.kongjak.kongjak.models.TechStack
import com.kongjak.kongjak.utils.DevIcons.KOTLIN

const val GITHUB_URL = "https://github.com/kongwoojin/"
const val TELEGRAM_URL = "https://t.me/Kongjak"
const val MAIL_ADDRESS = "mailto:kongjak@kongjak.com"

val languages = listOf(
    Skill("Kotlin", "images/skills/kotlin.svg"),
    Skill("Python", "images/skills/python.svg"),
    Skill("Java", "images/skills/java.svg"),
    Skill("Go", "images/skills/go.svg"),
    Skill("PHP", "images/skills/php.svg")
)

val tools = listOf(
    Skill("IntelliJ IDEA", "images/skills/intellijidea.svg"),
    Skill("Android Studio", "images/skills/androidstudio.svg"),
    Skill("PyCharm", "images/skills/pycharm.svg"),
    Skill("VSCode", "images/skills/vscodium.svg"),
    Skill("Git", "images/skills/git.svg")
)

val platformsAndFrameworks = listOf(
    Skill("Android", "images/skills/android.svg"),
    Skill("Jetpack Compose", "images/skills/jetpackcompose.svg"),
    Skill("Arduino", "images/skills/arduino.svg"),
    Skill("FastAPI", "images/skills/fastapi.svg"),
    Skill("Flask", "images/skills/flask.svg"),
    Skill("Nginx", "images/skills/nginx.svg")
)

val projectLists = listOf(
    Project(
        "Koreatech Board (Android)",
        "Unofficial android client for Koreatech board",
        listOf(
            ProjectUrl("#", "Android", ANDROID),
            ProjectUrl("https://github.com/kongwoojin/koreatech-board-android", "Github", GITHUB)
        ),
        listOf(
            TechStack(
                "Kotlin",
                KOTLIN
            ),
            TechStack(
                "Jetpack Compose",
                DevIcons.JETPACK_COMPOSE
            )
        )
    ),
    Project(
        "Koreatech Board (API)",
        "Unofficial API Wrapper for Koreatech Board",
        listOf(
            ProjectUrl("https://github.com/kongwoojin/koreatech-board-api", "Github", GITHUB)
        ),
        listOf(
            TechStack(
                "Go",
                DevIcons.GO
            )
        )
    ),
    Project(
        "Koreatech Board (Crawler)",
        "Koreatech Board crawler",
        listOf(ProjectUrl("https://github.com/kongwoojin/koreatech-board-crawler", "Github", GITHUB)),
        listOf(
            TechStack(
                "Python",
                DevIcons.PYTHON
            )
        )
    ),
    Project(
        "gobuild",
        "Cli tool for cross-compile Go",
        listOf(ProjectUrl("https://github.com/kongwoojin/gobuild", "Github", GITHUB)),
        listOf(
            TechStack(
                "Go",
                DevIcons.GO
            )
        )
    ),
    Project(
        "LineageOS for EF65S",
        "LineageOS 16.0 and 17.1 for Pantech Vega Pop-Up Note",
        listOf(
            ProjectUrl("https://dl.kongjak.com/ef65/LineageOS/", "Download", DOWNLOAD),
            ProjectUrl("https://github.com/sky-vega-dev-team", "Github", GITHUB)
        )
    ),
    Project(
        "Pantech Archive",
        "Archive of Firmwares & OpenSources for Pantech devices",
        listOf(ProjectUrl("https://pantech.kongjak.com", "Download", DOWNLOAD))
    ),
    Project(
        "More",
        "See more project on my Github!",
        listOf(ProjectUrl("https://github.com/kongwoojin", "Github", GITHUB))
    ),
)

