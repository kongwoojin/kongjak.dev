package dev.kongjak.kongjak.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kongjak.kongjak.components.sections.CrumbPart
import dev.kongjak.kongjak.components.sections.NavHeader
import dev.kongjak.kongjak.components.sections.NavStyle
import dev.kongjak.kongjak.components.sections.SiteFooter
import dev.kongjak.kongjak.components.sections.TweaksPanel
import dev.kongjak.kongjak.generated.blogPosts
import dev.kongjak.kongjak.models.BlogPostMetadata
import dev.kongjak.kongjak.utils.formatLongDate
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobwebx.markdown.markdown
import kotlinx.browser.document
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLHeadingElement
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Article
import org.jetbrains.compose.web.dom.B
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H4
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Main
import org.jetbrains.compose.web.dom.Nav
import org.jetbrains.compose.web.dom.Ol
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
@Layout
fun BlogLayout(ctx: PageContext, content: @Composable ColumnScope.() -> Unit) {
    val markdown = ctx.markdown
    val frontMatter = markdown?.frontMatter
    val title = frontMatter?.get("title")?.singleOrNull() ?: "Blog"
    val date = frontMatter?.get("date")?.singleOrNull()
    val tags = frontMatter?.get("tags").orEmpty()
    val currentPost = blogPosts.firstOrNull { it.route == ctx.route.path }
    var tocItems by remember(ctx.route.path) { mutableStateOf(emptyList<TocItem>()) }
    var readTime by remember(ctx.route.path) { mutableStateOf(1) }

    val (prevPost, nextPost) = remember(ctx.route.path) { adjacentPosts(ctx.route.path) }

    LaunchedEffect(ctx.route.path) {
        document.title = "Kongjak - $title"
        (document.querySelector(".article > h1:first-child") as? HTMLElement)?.style?.display = "none"
        tocItems = buildTocItems()
        readTime = estimateReadTime()
    }

    val crumb = buildList {
        add(CrumbPart.Link("blog", "/blog"))
        currentPost?.categorySlug?.let { slug ->
            add(CrumbPart.Link(slug, "/blog/$slug"))
        }
        add(CrumbPart.Active(title.take(30)))
    }

    NavHeader(style = NavStyle.Blog(crumb = crumb))

    Main(attrs = { id("top"); classes("shell") }) {
        with(PageContentScope) {
            Section(attrs = { classes("article-head") }) {
                A(href = "/blog", attrs = { classes("back", "mono") }) { Text("전체 글 목록") }
                Div(attrs = { classes("a-meta") }) {
                    if (!date.isNullOrBlank()) {
                        Span(attrs = { classes("mono") }) {
                            Span(attrs = { classes("arrow") }) { Text("→") }
                            Text(" ${formatLongDate(date)}")
                        }
                    }
                    val categoryName = currentPost?.categoryName
                    if (categoryName != null) {
                        Span(attrs = { classes("mono", "cat") }) { Text(categoryName) }
                    }
                    Span(attrs = { classes("mono", "read") }) { Text("$readTime min read") }
                }
                H1(attrs = { classes("a-title") }) { Text(title) }
                if (tags.isNotEmpty()) {
                    Div(attrs = { classes("a-tags") }) {
                        tags.forEach { tag ->
                            Span(attrs = { classes("a-tag") }) { Text(tag) }
                        }
                    }
                }
            }

            Article(attrs = { classes("article") }) {
                if (tocItems.isNotEmpty()) {
                    Div(attrs = { classes("toc") }) {
                        H4 { Text("table of contents") }
                        Ol {
                            tocItems.forEach { item ->
                                Li {
                                    A(href = "#${item.id}") { Text(item.text) }
                                }
                            }
                        }
                    }
                }
                content()
            }

            if (prevPost != null || nextPost != null) {
                Nav(attrs = { classes("pager-bottom"); attr("aria-label", "이전/다음 글") }) {
                    PagerSlot(post = prevPost, kind = "prev", label = "이전 글")
                    PagerSlot(post = nextPost, kind = "next", label = "다음 글")
                }
            }
        }
        SiteFooter()
    }
    TweaksPanel(showDensity = false)
}

@Composable
private fun PagerSlot(post: BlogPostMetadata?, kind: String, label: String) {
    if (post != null) {
        A(href = post.route, attrs = { classes("pg", kind) }) {
            Div(attrs = { classes("k") }) { Text(label) }
            Div(attrs = { classes("t") }) { Text(post.title) }
        }
    } else {
        Div(attrs = { classes("pg", kind) }) {
            Div(attrs = { classes("k") }) { Text(label) }
            Div(attrs = { classes("t"); style { property("color", "var(--fg-faint)") } }) {
                Text("—")
            }
        }
    }
}

private data class TocItem(val id: String, val text: String, val level: Int)

private fun buildTocItems(): List<TocItem> {
    val headings = document.querySelectorAll(".article h2, .article h3")
    val seenIds = mutableSetOf<String>()
    val items = mutableListOf<TocItem>()

    for (index in 0 until headings.length) {
        val heading = headings.item(index) as? HTMLHeadingElement ?: continue
        val text = heading.textContent?.trim().orEmpty()
        if (text.isBlank()) continue

        val baseId = text
            .lowercase()
            .replace(Regex("[^a-z0-9가-힣\\s-]"), "")
            .trim()
            .replace(Regex("\\s+"), "-")
            .ifBlank { "section-${index + 1}" }
        var id = baseId
        var suffix = 2
        while (!seenIds.add(id)) {
            id = "$baseId-$suffix"
            suffix++
        }

        heading.id = id
        items += TocItem(
            id = id,
            text = text,
            level = heading.tagName.removePrefix("H").toIntOrNull() ?: 2,
        )
    }
    return items
}

private fun adjacentPosts(currentRoute: String): Pair<BlogPostMetadata?, BlogPostMetadata?> {
    val sorted = blogPosts.sortedByDescending { it.date }
    val idx = sorted.indexOfFirst { it.route == currentRoute }
    if (idx < 0) return null to null
    val newer = sorted.getOrNull(idx - 1) // newer post (smaller idx = more recent)
    val older = sorted.getOrNull(idx + 1)
    return older to newer
}

private fun estimateReadTime(): Int {
    val text = (document.querySelector(".article") as? HTMLElement)?.textContent.orEmpty()
    val charCount = text.length
    // Korean reading speed ~ 500 chars/min for prose
    return (charCount / 500).coerceAtLeast(1)
}

