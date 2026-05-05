package dev.kongjak.build

import java.io.File
import java.nio.file.Files
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

data class FeedConfig(
    val siteBaseUrl: String,
    val title: String,
    val description: String,
    val language: String,
    val maxItems: Int = 20,
)

private val rfc822 = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
private val seoulZone: ZoneId = ZoneId.of("Asia/Seoul")

private fun isoDateToRfc822(iso: String): String =
    runCatching { LocalDate.parse(iso).atStartOfDay(seoulZone).format(rfc822) }
        .getOrElse { iso }

private val filenameRegex = Regex("[A-Za-z0-9_]+")

fun generateBlogMetadata(
    blogMarkdownDir: File,
    metadataDir: File,
    feedDir: File,
    feedConfig: FeedConfig,
) {
    val outputDir = metadataDir.resolve("dev/kongjak/kongjak/generated").apply { mkdirs() }
    val categoryPagesDir = metadataDir.resolve("dev/kongjak/kongjak/pages/blog/categories").apply { mkdirs() }
    categoryPagesDir.listFiles()?.forEach { it.delete() }

    val markdownFiles = blogMarkdownDir.walkTopDown()
        .filter { it.isFile && (it.extension == "md" || it.extension == "markdown") }
        .toList()
        .sortedBy { it.relativeTo(blogMarkdownDir).invariantSeparatorsPath }

    val posts = markdownFiles.map { file -> parsePost(file, blogMarkdownDir) }
        .sortedByDescending { it.getValue("date") }

    val categoryPosts = posts
        .filter { it.getValue("categorySlug").isNotBlank() }
        .groupBy { it.getValue("categorySlug") }
        .toSortedMap()

    outputDir.resolve("BlogPosts.kt").writeText(buildBlogPostsKt(posts, categoryPosts))

    categoryPosts.forEach { (slug, groupedPosts) ->
        val className = slug
            .split('-', '_')
            .joinToString("") { token -> token.replaceFirstChar { it.uppercase() } } + "CategoryPage"
        val categoryTitle = groupedPosts.first().getValue("categoryName")
        categoryPagesDir.resolve("$className.kt")
            .writeText(buildCategoryPageKt(slug, categoryTitle, className))
    }

    val feedFile = feedDir.resolve("public/feed.xml")
    feedFile.parentFile.mkdirs()
    val feedXml = buildFeedXml(posts, feedConfig)
    if (!feedFile.exists() || feedFile.readText() != feedXml) {
        feedFile.writeText(feedXml)
    }
}

private fun parsePost(file: File, blogMarkdownDir: File): Map<String, String> {
    val relativePath = file.relativeTo(blogMarkdownDir).invariantSeparatorsPath
    require(relativePath.substringBeforeLast('.').split('/').all { filenameRegex.matches(it) }) {
        "Blog markdown filenames may only contain letters, numbers, and underscores: $relativePath"
    }
    require(file.nameWithoutExtension != "index") {
        "Blog markdown filename 'index' is reserved because category routes use /blog/<category>: $relativePath"
    }

    // Kobweb keeps folder-segment underscores in route paths but converts
    // file-stem underscores to dashes. Match its behavior so the metadata
    // route lines up with the actual auto-generated page URL.
    val pathSegments = relativePath.substringBeforeLast('.').split('/')
    val routePath = (pathSegments.dropLast(1) + pathSegments.last().replace('_', '-'))
        .joinToString("/")
    val route = "/blog/$routePath"

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

    return mapOf(
        "title" to (frontMatter["title"]?.takeIf { it.isNotBlank() } ?: inferTitle(body, fallbackTitle)),
        "route" to route,
        "date" to date,
        "description" to (frontMatter["description"] ?: inferDescription(body)),
        "tags" to frontMatter["tags"].orEmpty(),
        "categorySlug" to categorySlug.orEmpty(),
        "categoryName" to categoryName.orEmpty(),
    )
}

private fun StringBuilder.appendBlogPostMetadata(post: Map<String, String>, indent: String, slugOverride: String? = null) {
    val tags = parseListValue(post.getValue("tags"))
        .joinToString(", ") { "\"${it.escapeKotlinString()}\"" }
    val effectiveSlug = slugOverride ?: post.getValue("categorySlug")
    val effectiveName = post.getValue("categoryName")

    appendLine("${indent}BlogPostMetadata(")
    appendLine("$indent    title = \"${post.getValue("title").escapeKotlinString()}\",")
    appendLine("$indent    route = \"${post.getValue("route").escapeKotlinString()}\",")
    appendLine("$indent    date = \"${post.getValue("date").escapeKotlinString()}\",")
    appendLine("$indent    description = \"${post.getValue("description").escapeKotlinString()}\",")
    appendLine("$indent    tags = listOf($tags),")
    if (slugOverride != null) {
        appendLine("$indent    categorySlug = \"${effectiveSlug.escapeKotlinString()}\",")
        appendLine("$indent    categoryName = \"${effectiveName.escapeKotlinString()}\",")
    } else {
        appendLine(
            "$indent    categorySlug = ${effectiveSlug.takeIf { it.isNotBlank() }?.let { "\"${it.escapeKotlinString()}\"" } ?: "null"},"
        )
        appendLine(
            "$indent    categoryName = ${effectiveName.takeIf { it.isNotBlank() }?.let { "\"${it.escapeKotlinString()}\"" } ?: "null"},"
        )
    }
    appendLine("$indent),")
}

private fun buildBlogPostsKt(
    posts: List<Map<String, String>>,
    categoryPosts: Map<String, List<Map<String, String>>>,
): String = buildString {
    appendLine("package dev.kongjak.kongjak.generated")
    appendLine()
    appendLine("import dev.kongjak.kongjak.models.BlogPostMetadata")
    appendLine()
    appendLine("val blogPosts = listOf(")
    posts.forEach { appendBlogPostMetadata(it, indent = "    ") }
    appendLine(")")
    appendLine()
    appendLine("val blogCategories = mapOf(")
    categoryPosts.forEach { (slug, groupedPosts) ->
        appendLine("    \"$slug\" to listOf(")
        groupedPosts.forEach { post -> appendBlogPostMetadata(post, indent = "        ", slugOverride = slug) }
        appendLine("    ),")
    }
    appendLine(")")
}

private fun buildCategoryPageKt(slug: String, categoryTitle: String, className: String): String = buildString {
    appendLine("package dev.kongjak.kongjak.pages.blog.categories")
    appendLine()
    appendLine("import androidx.compose.runtime.Composable")
    appendLine("import dev.kongjak.kongjak.components.layouts.PageLayoutData")
    appendLine("import dev.kongjak.kongjak.components.sections.CrumbPart")
    appendLine("import dev.kongjak.kongjak.components.sections.blog.BlogListing")
    appendLine("import dev.kongjak.kongjak.generated.blogCategories")
    appendLine("import com.varabyte.kobweb.core.Page")
    appendLine("import com.varabyte.kobweb.core.rememberPageContext")
    appendLine("import com.varabyte.kobweb.core.data.add")
    appendLine("import com.varabyte.kobweb.core.init.InitRoute")
    appendLine("import com.varabyte.kobweb.core.init.InitRouteContext")
    appendLine("import com.varabyte.kobweb.core.layout.Layout")
    appendLine()
    appendLine("@InitRoute")
    appendLine("fun init$className(ctx: InitRouteContext) {")
    appendLine("    ctx.data.add(")
    appendLine("        PageLayoutData(")
    appendLine("            title = \"$categoryTitle\",")
    appendLine("            crumb = listOf(")
    appendLine("                CrumbPart.Link(\"blog\", \"/blog\"),")
    appendLine("                CrumbPart.Active(\"$slug\"),")
    appendLine("            ),")
    appendLine("        )")
    appendLine("    )")
    appendLine("}")
    appendLine()
    appendLine("@Page(\"/blog/$slug\")")
    appendLine("@Layout(\".components.layouts.PageLayout\")")
    appendLine("@Composable")
    appendLine("fun $className() {")
    appendLine("    BlogListing(")
    appendLine("        pageContext = rememberPageContext(),")
    appendLine("        posts = blogCategories.getValue(\"$slug\"),")
    appendLine("        heading = \"$categoryTitle\",")
    appendLine("        description = \"\",")
    appendLine("    )")
    appendLine("}")
}

private fun buildFeedXml(posts: List<Map<String, String>>, config: FeedConfig): String = buildString {
    appendLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
    appendLine("<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\">")
    appendLine("  <channel>")
    appendLine("    <title>${config.title.xmlEscape()}</title>")
    appendLine("    <link>${config.siteBaseUrl}/blog</link>")
    appendLine("    <description>${config.description.xmlEscape()}</description>")
    appendLine("    <language>${config.language}</language>")
    appendLine("    <atom:link href=\"${config.siteBaseUrl}/feed.xml\" rel=\"self\" type=\"application/rss+xml\"/>")
    val mostRecent = posts.firstOrNull()?.getValue("date")
    if (!mostRecent.isNullOrBlank()) {
        appendLine("    <lastBuildDate>${isoDateToRfc822(mostRecent)}</lastBuildDate>")
    }
    posts.take(config.maxItems).forEach { post ->
        val link = "${config.siteBaseUrl}${post.getValue("route")}"
        val date = post.getValue("date")
        val description = post.getValue("description")
        val category = post.getValue("categoryName")
        appendLine("    <item>")
        appendLine("      <title>${post.getValue("title").xmlEscape()}</title>")
        appendLine("      <link>${link.xmlEscape()}</link>")
        appendLine("      <guid isPermaLink=\"true\">${link.xmlEscape()}</guid>")
        if (date.isNotBlank()) appendLine("      <pubDate>${isoDateToRfc822(date)}</pubDate>")
        if (category.isNotBlank()) appendLine("      <category>${category.xmlEscape()}</category>")
        if (description.isNotBlank()) {
            appendLine("      <description>${description.xmlEscape()}</description>")
        }
        appendLine("    </item>")
    }
    appendLine("  </channel>")
    appendLine("</rss>")
}
