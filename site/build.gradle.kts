import dev.kongjak.build.FeedConfig
import dev.kongjak.build.fetchAndGenerateContributions
import dev.kongjak.build.generateBlogMetadata
import dev.kongjak.build.generateBuildInfo
import dev.kongjak.build.resolveGitHeadRefFile
import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import kotlinx.html.script
import kotlinx.html.unsafe

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
}

group = "dev.kongjak.kongjak"
version = "1.0-SNAPSHOT"

// ----- paths -----

val blogMarkdownDir = layout.projectDirectory.dir("src/jsMain/resources/markdown/blog")
val generatedBlogMetadataDir = layout.buildDirectory.dir("generated/blog-metadata/src/jsMain/kotlin")
val generatedBlogFeedDir = layout.buildDirectory.dir("generated/blog-feed/jsMain/resources")
val generatedContributionsDir = layout.buildDirectory.dir("generated/github-contributions/src/jsMain/kotlin")
val generatedBuildInfoDir = layout.buildDirectory.dir("generated/build-info/src/jsMain/kotlin")
val contributionsCacheFile = layout.buildDirectory.file("cache/github-contributions.json")

// ----- site config -----

val githubUsername = "kongwoojin"
val contributionsCacheTtlMillis = 12 * 60 * 60 * 1000L

val feedConfig = FeedConfig(
    siteBaseUrl = "https://kongjak.com",
    title = "Kongjak Blog",
    description = "안드로이드 개발자 되기",
    language = "ko",
)

// ----- code-generation tasks -----

val generateBlogMetadata = tasks.register("generateBlogMetadata") {
    inputs.dir(blogMarkdownDir)
    outputs.dir(generatedBlogMetadataDir)
    outputs.dir(generatedBlogFeedDir)

    doLast {
        generateBlogMetadata(
            blogMarkdownDir = blogMarkdownDir.asFile,
            metadataDir = generatedBlogMetadataDir.get().asFile,
            feedDir = generatedBlogFeedDir.get().asFile,
            feedConfig = feedConfig,
        )
    }
}

val fetchGithubContributions = tasks.register("fetchGithubContributions") {
    val outFile = generatedContributionsDir.get().asFile
        .resolve("dev/kongjak/kongjak/generated/GithubContributions.kt")
    val cacheFile = contributionsCacheFile.get().asFile

    inputs.property("username", githubUsername)
    inputs.property("ttlMillis", contributionsCacheTtlMillis)
    outputs.file(outFile)
    outputs.upToDateWhen {
        outFile.exists() &&
            cacheFile.exists() &&
            (System.currentTimeMillis() - cacheFile.lastModified()) < contributionsCacheTtlMillis
    }

    doLast {
        fetchAndGenerateContributions(
            username = githubUsername,
            cacheFile = cacheFile,
            cacheTtlMillis = contributionsCacheTtlMillis,
            outFile = outFile,
            logger = logger,
        )
    }
}

val generateBuildInfo = tasks.register("generateBuildInfo") {
    val outFile = generatedBuildInfoDir.get().asFile
        .resolve("dev/kongjak/kongjak/generated/BuildInfo.kt")

    // Re-run when HEAD or the ref it points to changes (covers checkouts and new commits).
    val headFile = rootDir.resolve(".git/HEAD")
    if (headFile.exists()) inputs.file(headFile)
    resolveGitHeadRefFile(rootDir)?.let { inputs.file(it) }
    outputs.file(outFile)

    doLast { generateBuildInfo(rootDir, outFile, logger) }
}

// ----- Kobweb / Kotlin / source set wiring -----

kobweb {
    app {
        index {
            head.add {
                script {
                    unsafe {
                        +"(function(){var d=document.documentElement;var s=localStorage.getItem('kongjak:colorMode');d.setAttribute('data-theme',s==='LIGHT'?'light':'dark');var t={palette:'android',density:'regular',texture:'none'};try{var r=localStorage.getItem('kongjak:tweaks');if(r){var p=JSON.parse(r);if(typeof p==='object'){if(p.palette)t.palette=p.palette;if(p.density)t.density=p.density;if(p.texture)t.texture=p.texture}}}catch(e){}d.setAttribute('data-palette',t.palette);if(t.density!=='regular')d.setAttribute('data-density',t.density);if(t.texture!=='grid')d.setAttribute('data-texture',t.texture);})()"
                    }
                }
                link {
                    rel = "preconnect"
                    href = "https://fonts.googleapis.com"
                }
                link {
                    rel = "preconnect"
                    attributes["href"] = "https://fonts.gstatic.com"
                    attributes["crossorigin"] = ""
                }
                link {
                    rel = "stylesheet"
                    href = "https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@300;400;500;600;700&display=swap"
                }
                link {
                    rel = "stylesheet"
                    href =
                        "https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/variable/pretendardvariable-dynamic-subset.min.css"
                }
                link {
                    rel = "stylesheet"
                    href = "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/devicon.min.css"
                }
                link {
                    rel = "alternate"
                    attributes["type"] = "application/rss+xml"
                    attributes["title"] = "${feedConfig.title} RSS"
                    href = "/feed.xml"
                }
            }
            description.set("Powered by Kobweb")
        }
    }
}

kotlin {
    configAsKobwebApplication("kongjak")

    sourceSets {
        jsMain {
            kotlin.srcDir(generatedBlogMetadataDir)
            kotlin.srcDir(generatedContributionsDir)
            kotlin.srcDir(generatedBuildInfoDir)
            resources.srcDir(generatedBlogFeedDir)

            dependencies {
                implementation(libs.compose.runtime)
                implementation(libs.compose.html.core)
                implementation(libs.kobweb.core)
                implementation(libs.kobweb.silk)
                implementation(libs.silk.icons.fa)
                implementation(libs.kobwebx.markdown)
            }
        }
    }
}

tasks.matching { it.name.contains("KotlinJs") }.configureEach {
    dependsOn(generateBlogMetadata)
    dependsOn(fetchGithubContributions)
    dependsOn(generateBuildInfo)
}
