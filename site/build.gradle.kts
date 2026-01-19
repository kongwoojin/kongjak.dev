import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
}

group = "com.kongjak.kongjak"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            head.add {
                link {
                    rel = "stylesheet"
                    href =
                        "https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/variable/pretendardvariable-dynamic-subset.min.css"
                }
                link {
                    rel = "stylesheet"
                    href = "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/devicon.min.css"
                }
            }
            description.set("Powered by Kobweb")
        }
    }
}

kotlin {
    configAsKobwebApplication("kongjak")

    sourceSets {
        jsMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.silk.icons.fa)
            implementation(libs.kobwebx.markdown)
        }
    }
}
