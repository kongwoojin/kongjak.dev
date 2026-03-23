package com.kongjak.kongjak.components.sections.blog

import androidx.compose.runtime.Composable
import com.kongjak.kongjak.models.BlogPostMetadata
import com.kongjak.kongjak.pages.blog.BlogCardStyle
import com.kongjak.kongjak.pages.blog.BlogHeroLabelStyle
import com.kongjak.kongjak.toSitePalette
import com.kongjak.kongjak.utils.parseTagQuery
import com.kongjak.kongjak.utils.slugifyTag
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.window
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun BlogListing(
    pageContext: PageContext,
    posts: List<BlogPostMetadata>,
    heading: String,
    description: String,
) {
    val colorMode = ColorMode.current
    val currentTagSlug = parseTagQuery(window.location.search)
    val filteredPosts = posts.filter { post ->
        currentTagSlug == null || post.tags.any { slugifyTag(it) == currentTagSlug }
    }
    val currentCategorySlug = posts.firstOrNull()?.categorySlug?.takeIf { pageContext.route.path == "/blog/$it" }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .minHeight(100.vh)
            .padding(top = 8.cssRem, bottom = 5.cssRem, leftRight = 24.px),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .maxWidth(1180.px)
                .gap(28.px),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
        ) {
            BlogSidebar(
                availablePosts = posts,
                currentCategorySlug = currentCategorySlug,
                currentTagSlug = currentTagSlug,
                basePath = pageContext.route.path,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .maxWidth(820.px)
                    .classNames("blog-content-panel")
                    .backgroundColor(colorMode.toSitePalette().nearBackground)
                    .borderRadius(14.px)
                    .padding(28.px),
                horizontalAlignment = Alignment.Start,
            ) {
                P(attrs = BlogHeroLabelStyle.toAttrs()) {
                    Text("BLOG")
                }
                H1(
                    attrs = Modifier
                        .fontSize(3.cssRem)
                        .fontWeight(FontWeight.Bold)
                        .letterSpacing((-0.03).em)
                        .margin(top = 12.px, bottom = 10.px)
                        .textAlign(TextAlign.Start)
                        .toAttrs()
                ) {
                    Text(heading)
                }
                if (description.isNotBlank()) {
                    P(
                        attrs = Modifier
                            .color(colorMode.toSitePalette().description)
                            .margin(top = 0.px, bottom = 28.px)
                            .textAlign(TextAlign.Start)
                            .toAttrs()
                    ) {
                        Text(description)
                    }
                }

                if (posts.isEmpty()) {
                    P(
                        attrs = Modifier
                            .margin(0.px)
                            .color(colorMode.toSitePalette().description)
                            .toAttrs()
                    ) {
                        Text("No posts yet.")
                    }
                    return@Column
                }

                if (currentTagSlug != null) {
                    P(
                        attrs = Modifier
                            .margin(top = 0.px, bottom = 24.px)
                            .color(colorMode.toSitePalette().accent)
                            .fontWeight(FontWeight.Medium)
                            .toAttrs()
                    ) {
                        Text("Filtered by tag: #$currentTagSlug")
                    }
                }

                if (filteredPosts.isEmpty()) {
                    P(
                        attrs = Modifier
                            .margin(0.px)
                            .color(colorMode.toSitePalette().description)
                            .toAttrs()
                    ) {
                        Text("No posts match this tag.")
                    }
                    return@Column
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .margin(top = 16.px)
                        .gap(20.px),
                    verticalArrangement = Arrangement.spacedBy(20.px),
                ) {
                    filteredPosts.forEach { post ->
                        Link(path = post.route, modifier = BlogCardStyle.toModifier()) {
                            Column(modifier = Modifier.fillMaxWidth().gap(12.px)) {
                                P(
                                    attrs = Modifier
                                        .margin(0.px)
                                        .color(colorMode.toSitePalette().accent)
                                        .fontSize(0.8.cssRem)
                                        .toAttrs()
                                ) {
                                    Text(buildString {
                                        append(post.date)
                                        if (post.categoryName != null) {
                                            append(" / ")
                                            append(post.categoryName)
                                        }
                                    })
                                }

                                P(
                                    attrs = Modifier
                                        .margin(0.px)
                                        .color(colorMode.toSitePalette().title)
                                        .fontSize(1.5.cssRem)
                                        .fontWeight(FontWeight.Bold)
                                        .lineHeight(1.3)
                                        .toAttrs()
                                ) {
                                    Text(post.title)
                                }

                                if (post.description.isNotBlank()) {
                                    P(
                                        attrs = Modifier
                                            .fillMaxWidth()
                                            .display(DisplayStyle.Block)
                                            .classNames("blog-card-description")
                                            .margin(0.px)
                                            .color(colorMode.toSitePalette().description)
                                            .lineHeight(1.7)
                                            .toAttrs {
                                                style {
                                                    property("white-space", "nowrap")
                                                    property("overflow", "hidden")
                                                    property("text-overflow", "ellipsis")
                                                }
                                            }
                                    ) {
                                        Text(post.description)
                                    }
                                }

                                if (post.tags.isNotEmpty()) {
                                    P(
                                        attrs = Modifier
                                            .margin(0.px)
                                            .color(colorMode.toSitePalette().accent)
                                            .fontSize(0.9.cssRem)
                                            .lineHeight(1.8)
                                            .toAttrs()
                                    ) {
                                        Text(post.tags.joinToString("  ") { "#$it" })
                                    }
                                }

                                P(
                                    attrs = Modifier
                                        .margin(0.px)
                                        .color(colorMode.toSitePalette().accent)
                                        .fontWeight(FontWeight.Medium)
                                        .toAttrs()
                                ) {
                                    Text("Read post")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
