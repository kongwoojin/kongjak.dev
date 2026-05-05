package dev.kongjak.kongjak.components.sections.blog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.kongjak.kongjak.generated.blogPosts
import dev.kongjak.kongjak.models.BlogPostMetadata
import dev.kongjak.kongjak.utils.extractYear
import dev.kongjak.kongjak.utils.formatShortDate
import dev.kongjak.kongjak.utils.parseTagQuery
import dev.kongjak.kongjak.utils.slugifyTag
import com.varabyte.kobweb.core.PageContext
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Aside
import org.jetbrains.compose.web.dom.B
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.TagElement
import org.w3c.dom.HTMLElement
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul

@Composable
fun BlogListing(
    pageContext: PageContext,
    posts: List<BlogPostMetadata>,
    heading: String,
    description: String,
) {
    val currentTagSlug = remember { parseTagQuery(window.location.search) }
    val groupedByYear = remember(posts, currentTagSlug) {
        posts
            .filter { post ->
                currentTagSlug == null || post.tags.any { slugifyTag(it) == currentTagSlug }
            }
            .groupBy { extractYear(it.date) }
            .entries
            .sortedByDescending { it.key }
            .toList()
    }
    val filteredCount = groupedByYear.sumOf { it.value.size }

    BlogHead(heading = heading, description = description, totalPosts = posts.size)

    Div(attrs = { classes("blog-layout") }) {
        Div(attrs = { classes("main-col") }) {
            Div(attrs = { classes("toolbar") }) {
                Label(attrs = { classes("search") }) {
                    Span(attrs = { classes("pl") }) { Text("$") }
                    Span(attrs = { classes("mono"); style { property("color", "var(--fg-mute)") } }) {
                        Text("grep")
                    }
                    Input(type = org.jetbrains.compose.web.attributes.InputType.Search) {
                        attr("placeholder", "제목, 내용으로 검색…")
                        attr("disabled", "")
                    }
                    TagElement<HTMLElement>(
                        tagName = "kbd",
                        applyAttrs = null,
                        content = { Text("/") }
                    )
                }
            }

            if (currentTagSlug != null) {
                P(attrs = {
                    classes("mono")
                    style {
                        property("margin", "0 0 16px")
                        property("color", "var(--accent)")
                        property("font-size", "12.5px")
                    }
                }) {
                    Text("# tag: $currentTagSlug · $filteredCount posts")
                }
            }

            if (groupedByYear.isEmpty()) {
                Div(attrs = { classes("empty") }) {
                    Span(attrs = { classes("pl") }) { Text("$") }
                    Text(" grep -r \"${currentTagSlug ?: ""}\" ~/posts ")
                    Span(attrs = { style { property("color", "var(--fg-faint)") } }) {
                        Text("→ no matches found")
                    }
                }
            } else {
                groupedByYear.forEach { (year, yearPosts) ->
                    Section(attrs = { classes("year-group") }) {
                        Div(attrs = { classes("year-label") }) {
                            B { Text(year) }
                            Span { Text("· ${yearPosts.size} posts") }
                            Span(attrs = { classes("rule") }) {}
                        }
                        yearPosts.forEach { post -> BlogPostRow(post) }
                    }
                }
            }
        }

        Aside(attrs = { classes("side") }) {
            BlogSidebarTerminal(posts = blogPosts, currentTag = currentTagSlug)
        }
    }
}

private val blogYearRange: Pair<Int, Int>? by lazy {
    val years = blogPosts.mapNotNull { extractYear(it.date).toIntOrNull() }
    if (years.isEmpty()) null else years.min() to years.max()
}

@Composable
private fun BlogHead(heading: String, description: String, totalPosts: Int) {
    Section(attrs = { classes("blog-head") }) {
        Div(attrs = { classes("meta") }) {
            Span {
                Span(attrs = { classes("arrow") }) { Text("→") }
                Text(" blog.kongjak.dev")
            }
        }
        H1 { Text(heading) }
        if (description.isNotBlank()) {
            P(attrs = {
                style {
                    property("color", "var(--fg-soft)")
                    property("font-size", "15px")
                    property("margin", "0")
                    property("max-width", "56ch")
                }
            }) { Text(description) }
        }
        Div(attrs = { classes("stats") }) {
            Span {
                B { Text(totalPosts.toString()) }
                Text(" posts")
            }
            blogYearRange?.let { (min, max) ->
                Span {
                    B { Text(min.toString()) }
                    Text(" → ")
                    B { Text(max.toString()) }
                }
            }
        }
    }
}

@Composable
private fun BlogPostRow(post: BlogPostMetadata) {
    A(href = post.route, attrs = { classes("blog-post") }) {
        Div(attrs = { classes("post-date") }) { Text(formatShortDate(post.date)) }
        Div(attrs = { classes("post-body") }) {
            H2(attrs = { classes("post-title") }) { Text(post.title) }
            if (post.description.isNotBlank()) {
                P(attrs = { classes("post-excerpt") }) { Text(post.description) }
            }
        }
        Div(attrs = { classes("post-meta") }) {
            Span(attrs = { classes("cat") }) {
                val cat = post.categoryName
                if (cat != null) Text(cat) else Text("blog")
            }
        }
    }
}

private data class CategoryEntry(val slug: String, val name: String, val count: Int)

@Composable
private fun BlogSidebarTerminal(posts: List<BlogPostMetadata>, currentTag: String?) {
    val orderedCategories = remember(posts) {
        posts.groupBy { it.categorySlug to it.categoryName }
            .entries
            .filter { it.key.first != null }
            .sortedByDescending { it.value.size }
            .map { (key, list) -> CategoryEntry(key.first!!, key.second ?: key.first!!, list.size) }
    }
    val allTags = remember(posts) {
        posts.flatMap { it.tags }
            .filter { it.isNotBlank() }
            .groupingBy { it }
            .eachCount()
            .entries
            .sortedByDescending { it.value }
            .map { it.key }
            .take(20)
    }

    Div(attrs = { classes("side-block") }) {
        H3 { Text("categories") }
        Ul(attrs = { classes("cats") }) {
            Li {
                A(href = "/blog") {
                    Span { Text("분류 전체보기") }
                    Span(attrs = { classes("ct") }) { Text(posts.size.toString()) }
                }
            }
            orderedCategories.forEach { entry ->
                Li {
                    A(href = "/blog/${entry.slug}") {
                        Span { Text(entry.name) }
                        Span(attrs = { classes("ct") }) { Text(entry.count.toString()) }
                    }
                }
            }
        }
    }

    if (allTags.isNotEmpty()) {
        Div(attrs = { classes("side-block") }) {
            H3 { Text("tags") }
            Div(attrs = { classes("tags") }) {
                allTags.forEach { tag ->
                    val slug = slugifyTag(tag)
                    A(href = "/blog?tag=$slug", attrs = {
                        classes("tag")
                        if (currentTag == slug) {
                            style { property("color", "var(--fg)") }
                        }
                    }) { Text(tag) }
                }
            }
        }
    }

    Div(attrs = { classes("side-block") }) {
        H3 { Text("links") }
        Ul(attrs = { classes("cats") }) {
            Li { A(href = "/") { Span { Text("↗ portfolio") } } }
            Li {
                A(href = "https://github.com/kongwoojin", attrs = {
                    attr("target", "_blank")
                    attr("rel", "noopener")
                }) { Span { Text("↗ github") } }
            }
            Li { A(href = "mailto:kongjak@kongjak.dev") { Span { Text("↗ email") } } }
            Li {
                A(href = "/feed.xml", attrs = {
                    attr("target", "_blank")
                    attr("rel", "noopener")
                }) { Span { Text("↗ rss") } }
            }
        }
    }
}

