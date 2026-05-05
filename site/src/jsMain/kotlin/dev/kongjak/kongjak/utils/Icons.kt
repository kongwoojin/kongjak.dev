package dev.kongjak.kongjak.utils

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.dom.I

@Composable
fun DevIcon(
    icon: String,
    modifier: Modifier = Modifier,
) = I(attrs = Modifier.classNames("devicon", icon).then(modifier).toAttrs())

object DevIcons {
    const val KOTLIN = "devicon-kotlin-plain"
    const val GO = "devicon-go-original-wordmark"
    const val PYTHON = "devicon-python-plain"
    const val C = "devicon-c-plain"
    const val JAVA = "devicon-java-plain"
    const val JETPACK_COMPOSE = "devicon-jetpackcompose-line"
    const val SWIFT = "devicon-swift-plain"
    const val SPRING = "devicon-spring-original"
    const val REACT = "devicon-react-original"
    const val TYPESCRIPT = "devicon-typescript-plain"
}
