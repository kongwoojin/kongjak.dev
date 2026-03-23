import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import java.nio.file.Files
import java.time.ZoneId

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
}

group = "com.kongjak.kongjak"
version = "1.0-SNAPSHOT"

val blogMarkdownDir = layout.projectDirectory.dir("src/jsMain/resources/markdown/blog")
val generatedBlogMetadataDir = layout.buildDirectory.dir("generated/blog-metadata/src/jsMain/kotlin")

fun String.escapeKotlinString(): String =
    buildString(length) {
        for (char in this@escapeKotlinString) {
            when (char) {
                '\\' -> append("\\\\")
                '"' -> append("\\\"")
                '\n' -> append("\\n")
                '\r' -> append("\\r")
                '\t' -> append("\\t")
                else -> append(char)
            }
        }
    }

fun parseSimpleFrontMatter(markdown: String): Map<String, String> {
    if (!markdown.startsWith("---\n") && !markdown.startsWith("---\r\n")) return emptyMap()

    val lines = markdown.lines()
    if (lines.firstOrNull()?.trim() != "---") return emptyMap()

    val result = linkedMapOf<String, String>()
    var currentKey: String? = null
    val currentList = mutableListOf<String>()

    fun flushList() {
        val key = currentKey ?: return
        if (currentList.isNotEmpty()) {
            result[key] = currentList.joinToString(", ")
            currentList.clear()
        }
        currentKey = null
    }

    for (line in lines.drop(1)) {
        val trimmed = line.trim()
        if (trimmed == "---") {
            flushList()
            break
        }

        if (trimmed.startsWith("- ") && currentKey != null) {
            currentList += trimmed.removePrefix("- ").trim().trim('"')
            continue
        }

        flushList()

        val separatorIndex = line.indexOf(':')
        if (separatorIndex <= 0) continue

        val key = line.substring(0, separatorIndex).trim()
        val rawValue = line.substring(separatorIndex + 1).trim()
        if (rawValue.startsWith("[") && rawValue.endsWith("]")) {
            result[key] = rawValue
                .removePrefix("[")
                .removeSuffix("]")
                .split(',')
                .map { it.trim().trim('"') }
                .filter { it.isNotEmpty() }
                .joinToString(", ")
        } else if (rawValue.isEmpty()) {
            currentKey = key
        } else {
            result[key] = rawValue.trim('"')
        }
    }

    return result
}

fun stripFrontMatter(markdown: String): String {
    if (!markdown.startsWith("---\n") && !markdown.startsWith("---\r\n")) return markdown
    val lines = markdown.lines()
    if (lines.firstOrNull()?.trim() != "---") return markdown

    val endIndex = lines.drop(1).indexOfFirst { it.trim() == "---" }
    return if (endIndex == -1) markdown else lines.drop(endIndex + 2).joinToString("\n")
}

fun inferTitle(markdownBody: String, fallback: String): String =
    markdownBody
        .lineSequence()
        .map { it.trim() }
        .firstOrNull { it.startsWith("# ") }
        ?.removePrefix("# ")
        ?.trim()
        ?.takeIf { it.isNotEmpty() }
        ?: fallback

fun inferDescription(markdownBody: String): String =
    markdownBody
        .lineSequence()
        .map { it.trim() }
        .filter { line ->
            line.isNotEmpty() &&
                !line.startsWith("#") &&
                !line.startsWith("![]") &&
                !line.startsWith("```") &&
                !line.startsWith(">") &&
                !line.startsWith("- ")
        }
        .firstOrNull()
        ?.replace(Regex("\\[(.*?)]\\((.*?)\\)"), "$1")
        ?.take(180)
        .orEmpty()

fun slugToTitle(slug: String): String =
    slug
        .split('_', '-')
        .joinToString(" ") { token -> token.replaceFirstChar { it.uppercase() } }

fun parseListValue(rawValue: String): List<String> =
    rawValue
        .split(',')
        .map { it.trim() }
        .filter { it.isNotEmpty() }

val generateBlogMetadata = tasks.register("generateBlogMetadata") {
    inputs.dir(blogMarkdownDir)
    outputs.dir(generatedBlogMetadataDir)

    doLast {
        val outputDir = generatedBlogMetadataDir.get().asFile.resolve("com/kongjak/kongjak/generated")
        outputDir.mkdirs()
        val generatedCategoryPagesDir = generatedBlogMetadataDir.get().asFile.resolve("com/kongjak/kongjak/pages/blog/categories")
        generatedCategoryPagesDir.mkdirs()
        generatedCategoryPagesDir.listFiles()?.forEach { it.delete() }

        val markdownFiles = blogMarkdownDir.asFileTree.matching {
            include("**/*.md", "**/*.markdown")
        }.files.sortedBy { it.relativeTo(blogMarkdownDir.asFile).invariantSeparatorsPath }

        val posts = markdownFiles.mapNotNull { file ->
            val relativePath = file.relativeTo(blogMarkdownDir.asFile).invariantSeparatorsPath
            require(relativePath.substringBeforeLast('.').split('/').all { segment -> segment.matches(Regex("[A-Za-z0-9_]+")) }) {
                "Blog markdown filenames may only contain letters, numbers, and underscores: $relativePath"
            }
            require(file.nameWithoutExtension != "index") {
                "Blog markdown filename 'index' is reserved because category routes use /blog/<category>: $relativePath"
            }
            val route = "/blog/${relativePath.substringBeforeLast('.').replace('_', '-')}"

            val markdown = file.readText()
            val frontMatter = parseSimpleFrontMatter(markdown)
            val body = stripFrontMatter(markdown)
            val fallbackTitle = file.nameWithoutExtension
                .split('-', '_')
                .joinToString(" ") { token -> token.replaceFirstChar { it.uppercase() } }

            val date = frontMatter["date"]
                ?.takeIf { it.isNotBlank() }
                ?: Files.getLastModifiedTime(file.toPath())
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .toString()

            val categorySlug = relativePath.substringBefore('/').takeIf { '/' in relativePath }?.replace('_', '-')
            val categoryName = categorySlug?.let(::slugToTitle)
            require(frontMatter["categories"].isNullOrBlank()) {
                "Blog post front matter 'categories' is no longer supported. Use the first subfolder as the category: $relativePath"
            }

            mapOf(
                "title" to (frontMatter["title"]?.takeIf { it.isNotBlank() } ?: inferTitle(body, fallbackTitle)),
                "route" to route,
                "date" to date,
                "description" to (frontMatter["description"] ?: inferDescription(body)),
                "tags" to frontMatter["tags"].orEmpty(),
                "categorySlug" to categorySlug.orEmpty(),
                "categoryName" to categoryName.orEmpty(),
            )
        }.sortedByDescending { it.getValue("date") }

        val categoryPosts = posts
            .filter { it.getValue("categorySlug").isNotBlank() }
            .groupBy { it.getValue("categorySlug") }
            .toSortedMap()

        val content = buildString {
            appendLine("package com.kongjak.kongjak.generated")
            appendLine()
            appendLine("import com.kongjak.kongjak.models.BlogPostMetadata")
            appendLine()
            appendLine("val blogPosts = listOf(")
            posts.forEach { post ->
                val tags = post.getValue("tags")
                    .let(::parseListValue)
                    .joinToString(", ") { "\"${it.escapeKotlinString()}\"" }

                appendLine("    BlogPostMetadata(")
                appendLine("        title = \"${post.getValue("title").escapeKotlinString()}\",")
                appendLine("        route = \"${post.getValue("route").escapeKotlinString()}\",")
                appendLine("        date = \"${post.getValue("date").escapeKotlinString()}\",")
                appendLine("        description = \"${post.getValue("description").escapeKotlinString()}\",")
                appendLine("        tags = listOf($tags),")
                appendLine("        categorySlug = ${post.getValue("categorySlug").takeIf { it.isNotBlank() }?.let { "\"${it.escapeKotlinString()}\"" } ?: "null"},")
                appendLine("        categoryName = ${post.getValue("categoryName").takeIf { it.isNotBlank() }?.let { "\"${it.escapeKotlinString()}\"" } ?: "null"},")
                appendLine("    ),")
            }
            appendLine(")")
            appendLine()
            appendLine("val blogCategories = mapOf(")
            categoryPosts.forEach { (slug, groupedPosts) ->
                appendLine("    \"$slug\" to listOf(")
                groupedPosts.forEach { post ->
                    val tags = post.getValue("tags")
                        .let(::parseListValue)
                        .joinToString(", ") { "\"${it.escapeKotlinString()}\"" }

                    appendLine("        BlogPostMetadata(")
                    appendLine("            title = \"${post.getValue("title").escapeKotlinString()}\",")
                    appendLine("            route = \"${post.getValue("route").escapeKotlinString()}\",")
                    appendLine("            date = \"${post.getValue("date").escapeKotlinString()}\",")
                    appendLine("            description = \"${post.getValue("description").escapeKotlinString()}\",")
                    appendLine("            tags = listOf($tags),")
                    appendLine("            categorySlug = \"${slug.escapeKotlinString()}\",")
                    appendLine("            categoryName = \"${post.getValue("categoryName").escapeKotlinString()}\",")
                    appendLine("        ),")
                }
                appendLine("    ),")
            }
            appendLine(")")
        }

        outputDir.resolve("BlogPosts.kt").writeText(content)

        categoryPosts.forEach { (slug, groupedPosts) ->
            val className = slug
                .split('-', '_')
                .joinToString("") { token -> token.replaceFirstChar { it.uppercase() } } + "CategoryPage"
            val categoryTitle = groupedPosts.first().getValue("categoryName")
            val pageContent = buildString {
                appendLine("package com.kongjak.kongjak.pages.blog.categories")
                appendLine()
                appendLine("import androidx.compose.runtime.Composable")
                appendLine("import com.kongjak.kongjak.components.layouts.PageLayoutData")
                appendLine("import com.kongjak.kongjak.components.sections.blog.BlogListing")
                appendLine("import com.kongjak.kongjak.generated.blogCategories")
                appendLine("import com.varabyte.kobweb.core.Page")
                appendLine("import com.varabyte.kobweb.core.rememberPageContext")
                appendLine("import com.varabyte.kobweb.core.data.add")
                appendLine("import com.varabyte.kobweb.core.init.InitRoute")
                appendLine("import com.varabyte.kobweb.core.init.InitRouteContext")
                appendLine("import com.varabyte.kobweb.core.layout.Layout")
                appendLine()
                appendLine("@InitRoute")
                appendLine("fun init$className(ctx: InitRouteContext) {")
                appendLine("    ctx.data.add(PageLayoutData(\"$categoryTitle\"))")
                appendLine("}")
                appendLine()
                appendLine("@Page(\"/blog/$slug\")")
                appendLine("@Layout(\".components.layouts.PageLayout\")")
                appendLine("@Composable")
                appendLine("fun $className() {")
                appendLine("    BlogListing(")
                appendLine("        pageContext = rememberPageContext(),")
                appendLine("        posts = blogCategories.getValue(\"$slug\"),")
                appendLine("        heading = \"$categoryTitle 게시글\",")
                appendLine("        description = \"\"")
                appendLine("    )")
                appendLine("}")
            }
            generatedCategoryPagesDir.resolve("$className.kt").writeText(pageContent)
        }
    }
}

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
        jsMain {
            kotlin.srcDir(generatedBlogMetadataDir)

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
}
