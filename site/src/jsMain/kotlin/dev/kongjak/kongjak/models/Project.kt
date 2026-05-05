package dev.kongjak.kongjak.models

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.silk.components.icons.fa.FaAndroid
import com.varabyte.kobweb.silk.components.icons.fa.FaDownload
import com.varabyte.kobweb.silk.components.icons.fa.FaGithub

data class Project(
    val name: String,
    val description: String,
    val position: String? = null,
    val parts: List<ProjectPart> = emptyList(),
    val techStacks: List<TechStack> = emptyList(),
)

data class ProjectPart(
    val name: String,
    val urls: List<ProjectUrl>,
    val techStacks: List<TechStack> = emptyList(),
)

data class ProjectUrl(
    val url: String,
    val urlName: String,
    val icon: FaIcons,
)

data class TechStack(
    val name: String,
    val icon: String,
)

enum class FaIcons {
    GITHUB,
    ANDROID,
    DOWNLOAD,
}

@Composable
fun getFaIcon(icon: FaIcons) = when (icon) {
    FaIcons.GITHUB -> FaGithub()
    FaIcons.ANDROID -> FaAndroid()
    FaIcons.DOWNLOAD -> FaDownload()
}
