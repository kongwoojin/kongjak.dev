package com.kongjak.kongjak.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kongjak.kongjak.components.sections.Footer
import com.kongjak.kongjak.components.sections.NavHeader
import com.kongjak.kongjak.generated.blogPosts
import com.kongjak.kongjak.toSitePalette
import com.kongjak.kongjak.utils.slugifyTag
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.gridRow
import com.varabyte.kobweb.compose.ui.modifiers.gridTemplateRows
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobwebx.markdown.markdown
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLHeadingElement
import org.w3c.dom.HTMLElement
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine

@Composable
@Layout
fun BlogLayout(ctx: PageContext, content: @Composable ColumnScope.() -> Unit) {
    val markdown = ctx.markdown
    val frontMatter = markdown?.frontMatter
    val title = frontMatter?.get("title")?.singleOrNull() ?: "Blog"
    val date = frontMatter?.get("date")?.singleOrNull()
    val description = frontMatter?.get("description")?.singleOrNull().orEmpty()
    val tags = frontMatter?.get("tags").orEmpty()
    val currentPost = blogPosts.firstOrNull { it.route == ctx.route.path }
    val categorySlug = currentPost?.categorySlug
    val tagBasePath = if (categorySlug != null) "/blog/$categorySlug" else "/blog"
    var tocItems by remember(ctx.route.path) { mutableStateOf(emptyList<TocItem>()) }
    val currentTagSlug = window.location.search
        .removePrefix("?")
        .split('&')
        .firstOrNull { it.startsWith("tag=") }
        ?.substringAfter('=')
        ?.replace("+", " ")
        ?.takeIf { it.isNotBlank() }
    val colorMode = ColorMode.current

    LaunchedEffect(title) {
        document.title = "Kongjak - $title"
        (document.querySelector(".blog-article > h1:first-child") as? HTMLElement)?.style?.display = "none"
        repeat(document.querySelectorAll(".blog-article pre").length) { index ->
            (document.querySelectorAll(".blog-article pre").item(index) as? HTMLElement)?.style?.apply {
                overflowX = "auto"
                maxWidth = "100%"
                boxSizing = "border-box"
            }
        }
        repeat(document.querySelectorAll(".blog-article blockquote").length) { index ->
            (document.querySelectorAll(".blog-article blockquote").item(index) as? HTMLElement)?.style?.apply {
                borderLeft = "3px solid rgba(148, 163, 184, 0.7)"
            }
        }
        tocItems = buildTocItems()
    }

    NavHeader()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .minHeight(100.vh)
            .gridTemplateRows { size(1.fr); size(minContent) },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize().gridRow(1),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .maxWidth(1360.px)
                    .padding(top = 8.cssRem, bottom = 5.cssRem, leftRight = 24.px)
                    .gap(28.px),
                verticalAlignment = Alignment.Top,
            ) {
                Box(
                    modifier = Modifier.width(12.cssRem)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .maxWidth(840.px)
                        .classNames("blog-content-panel")
                        .backgroundColor(colorMode.toSitePalette().nearBackground)
                        .borderRadius(14.px)
                        .padding(28.px)
                        .gap(18.px),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .classNames("blog-post-header")
                            .gap(10.px),
                    ) {
                        P(
                            attrs = Modifier
                                .color(colorMode.toSitePalette().accent)
                                .fontSize(0.82.cssRem)
                                .fontWeight(FontWeight.Bold)
                                .letterSpacing(0.16.em)
                                .margin(0.px)
                                .toAttrs()
                        ) {
                            Text("BLOG")
                        }

                        H1(
                            attrs = Modifier
                                .margin(0.px)
                                .color(colorMode.toSitePalette().title)
                                .fontSize(2.7.cssRem)
                                .fontWeight(FontWeight.Bold)
                                .lineHeight(1.18)
                                .letterSpacing((-0.03).em)
                                .toAttrs()
                        ) {
                            Text(title)
                        }

                        if (description.isNotBlank()) {
                            P(
                                attrs = Modifier
                                    .margin(top = 0.px, bottom = 4.px)
                                    .color(colorMode.toSitePalette().description)
                                    .lineHeight(1.7)
                                    .toAttrs()
                            ) {
                                Text(description)
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .classNames("blog-post-meta")
                                .gap(12.px),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            if (!date.isNullOrBlank()) {
                                MetaText(date, colorMode.toSitePalette().accent)
                            }

                            currentPost?.categorySlug?.let { slug ->
                                if (!date.isNullOrBlank()) {
                                    MetaSeparator()
                                }
                                Link(
                                    path = "/blog/$slug",
                                    modifier = Modifier
                                        .classNames("blog-meta-link")
                                        .color(colorMode.toSitePalette().accent)
                                        .fontSize(0.92.cssRem)
                                ) {
                                    Text(currentPost.categoryName ?: slug)
                                }
                            }

                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .classNames("blog-article")
                            .lineHeight(1.8)
                            .color(colorMode.toSitePalette().title)
                    ) {
                        content()
                    }

                    if (tags.isNotEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .classNames("blog-post-tags")
                                .gap(10.px),
                        ) {
                            P(
                                attrs = Modifier
                                    .margin(0.px)
                                    .color(colorMode.toSitePalette().accent)
                                    .fontSize(0.82.cssRem)
                                    .fontWeight(FontWeight.Bold)
                                    .letterSpacing(0.14.em)
                                    .toAttrs()
                            ) {
                                Text("TAGS")
                            }

                            P(
                                attrs = Modifier
                                    .margin(0.px)
                                    .lineHeight(1.9)
                                    .toAttrs()
                            ) {
                                tags.forEachIndexed { index, tag ->
                                    Link(
                                        path = "$tagBasePath?tag=${slugifyTag(tag)}",
                                        modifier = Modifier
                                            .classNames("blog-tag-link")
                                            .color(colorMode.toSitePalette().accent)
                                    ) {
                                        Text("#$tag")
                                    }
                                    if (index != tags.lastIndex) {
                                        Text(" ")
                                    }
                                }
                            }
                        }
                    }
                }

                if (tocItems.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .width(12.cssRem)
                            .classNames("blog-toc")
                            .gap(10.px),
                    ) {
                        P(
                            attrs = Modifier
                                .margin(0.px)
                                .color(colorMode.toSitePalette().accent)
                                .fontSize(0.82.cssRem)
                                .fontWeight(FontWeight.Bold)
                                .letterSpacing(0.14.em)
                                .toAttrs()
                        ) {
                            Text("ON THIS PAGE")
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth().gap(8.px),
                        ) {
                            tocItems.forEach { item ->
                                A(
                                    href = "#${item.id}",
                                    attrs = Modifier
                                        .classNames("blog-toc-link")
                                        .color(colorMode.toSitePalette().description)
                                        .fontSize(if (item.level == 2) 0.95.cssRem else 0.88.cssRem)
                                        .margin(left = if (item.level == 2) 0.px else 12.px)
                                        .lineHeight(1.5)
                                        .textDecorationLine(TextDecorationLine.None)
                                        .toAttrs()
                                ) {
                                    Text(item.text)
                                }
                            }
                        }
                    }
                }
            }
        }
        Footer(modifier = Modifier.fillMaxWidth().gridRow(2))
    }
}

@Composable
private fun MetaText(text: String, color: com.varabyte.kobweb.compose.ui.graphics.Color) {
    P(
        attrs = Modifier
            .margin(0.px)
            .color(color)
            .fontSize(0.92.cssRem)
            .toAttrs()
    ) {
        Text(text)
    }
}

@Composable
private fun MetaSeparator() {
    P(
        attrs = Modifier
            .margin(0.px)
            .fontSize(0.92.cssRem)
            .toAttrs()
    ) {
        Text("·")
    }
}

private data class TocItem(
    val id: String,
    val text: String,
    val level: Int,
)

private fun buildTocItems(): List<TocItem> {
    val headings = document.querySelectorAll(".blog-article h2, .blog-article h3")
    val seenIds = mutableSetOf<String>()
    val items = mutableListOf<TocItem>()

    for (index in 0 until headings.length) {
        val heading = headings.item(index) as? HTMLHeadingElement ?: continue
        val text = heading.textContent?.trim().orEmpty()
        if (text.isBlank()) continue

        val baseId = text
            .lowercase()
            .replace(Regex("[^a-z0-9\\s-]"), "")
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
        items += TocItem(id = id, text = text, level = heading.tagName.removePrefix("H").toIntOrNull() ?: 2)
    }

    return items
}
