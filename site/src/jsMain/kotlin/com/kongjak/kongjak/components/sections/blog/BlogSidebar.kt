package com.kongjak.kongjak.components.sections.blog

import androidx.compose.runtime.Composable
import com.kongjak.kongjak.models.BlogPostMetadata
import com.kongjak.kongjak.toSitePalette
import com.kongjak.kongjak.utils.slugifyTag
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun BlogSidebar(
    availablePosts: List<BlogPostMetadata>,
    currentCategorySlug: String?,
    currentTagSlug: String?,
    basePath: String,
) {
    val colorMode = ColorMode.current
    val palette = colorMode.toSitePalette()
    val categories = availablePosts
        .mapNotNull { post ->
            post.categorySlug?.let { slug ->
                slug to (post.categoryName ?: slug)
            }
        }
        .distinctBy { it.first }
        .sortedBy { it.second.lowercase() }
    val tags = availablePosts
        .flatMap { it.tags }
        .distinct()
        .sortedBy { it.lowercase() }

    Column(
        modifier = Modifier
            .classNames("blog-sidebar")
            .width(17.cssRem)
            .backgroundColor(palette.cardBackground)
            .border(1.px, LineStyle.Solid, palette.divider)
            .borderRadius(14.px)
            .padding(20.px)
            .gap(8.px),
        verticalArrangement = Arrangement.spacedBy(8.px),
    ) {
        P(
            attrs = Modifier
                .margin(bottom = 12.px, top = 0.px)
                .color(palette.accent)
                .fontWeight(FontWeight.Bold)
                .fontSize(0.82.cssRem)
                .letterSpacing(0.08.em)
                .toAttrs()
        ) {
            Text("CATEGORIES")
        }

        CategoryLink(
            label = "All posts",
            path = "/blog",
            selected = currentCategorySlug == null && currentTagSlug == null,
        )

        categories.forEach { (slug, name) ->
            CategoryLink(
                label = name,
                path = "/blog/$slug",
                selected = currentCategorySlug == slug && currentTagSlug == null,
            )
        }

        if (tags.isNotEmpty()) {
            P(
                attrs = Modifier
                    .margin(top = 18.px, bottom = 12.px)
                    .color(palette.accent)
                    .fontWeight(FontWeight.Bold)
                    .fontSize(0.82.cssRem)
                    .letterSpacing(0.08.em)
                    .toAttrs()
            ) {
                Text("TAGS")
            }

            Div(
                attrs = Modifier
                    .classNames("blog-sidebar-tags")
                    .display(DisplayStyle.Flex)
                    .flexWrap(FlexWrap.Wrap)
                    .gap(8.px)
                    .toAttrs()
            ) {
                TagLink(
                    label = "All tags",
                    path = basePath,
                    selected = currentTagSlug == null,
                )

                tags.forEach { tag ->
                    val tagSlug = slugifyTag(tag)
                    TagLink(
                        label = tag,
                        path = "$basePath?tag=$tagSlug",
                        selected = currentTagSlug == tagSlug,
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryLink(label: String, path: String, selected: Boolean) {
    val palette = ColorMode.current.toSitePalette()

    Link(
        path = path,
        modifier = Modifier
            .fillMaxWidth()
            .padding(topBottom = 9.px, leftRight = 12.px)
            .borderRadius(10.px)
            .backgroundColor(if (selected) palette.cardBackground else palette.nearBackground)
            .border(1.px, LineStyle.Solid, if (selected) palette.accent else palette.divider)
            .color(if (selected) palette.accent else palette.description)
            .fontWeight(if (selected) FontWeight.Bold else FontWeight.Normal)
    ) {
        Text(label)
    }
}

@Composable
private fun TagLink(label: String, path: String, selected: Boolean) {
    val palette = ColorMode.current.toSitePalette()

    Link(
        path = path,
        modifier = Modifier
            .padding(topBottom = 8.px, leftRight = 12.px)
            .borderRadius(10.px)
            .backgroundColor(if (selected) palette.cardBackground else palette.nearBackground)
            .border(1.px, LineStyle.Solid, if (selected) palette.accent else palette.divider)
            .color(if (selected) palette.accent else palette.description)
            .fontWeight(if (selected) FontWeight.Bold else FontWeight.Normal)
            .fontSize(0.92.cssRem)
    ) {
        Text("#$label")
    }
}
