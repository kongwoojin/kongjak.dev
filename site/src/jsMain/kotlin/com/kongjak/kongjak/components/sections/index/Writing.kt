package com.kongjak.kongjak.components.sections.index

import androidx.compose.runtime.Composable
import com.kongjak.kongjak.generated.blogPosts
import com.kongjak.kongjak.utils.formatLongDate
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun IndexWriting() {
    Section(attrs = { id("writing") }) {
        SectionHead("04", "writing")

        Div(attrs = { classes("writing-list") }) {
            for (post in blogPosts.take(4)) {
                A(
                    href = post.route,
                    attrs = { classes("post", "reveal") }
                ) {
                    Div(attrs = { classes("post-date") }) {
                        Text(formatLongDate(post.date))
                    }
                    Div(attrs = { classes("post-title") }) { Text(post.title) }
                    Div(attrs = { classes("post-meta") }) {
                        val meta = buildString {
                            if (post.categoryName != null) append(post.categoryName)
                            if (post.tags.isNotEmpty()) {
                                if (isNotEmpty()) append(" · ")
                                append(post.tags.first())
                            }
                        }
                        Text(meta)
                    }
                }
            }
        }

        P(attrs = {
            classes("mono")
            style { property("margin-top", "18px"); property("font-size", "12px"); property("color", "var(--fg-mute)") }
        }) {
            Span(attrs = { style { property("color", "var(--fg-faint)") } }) { Text("$ cd ") }
            A(
                href = "/blog",
                attrs = { style { property("color", "var(--fg)"); property("border-bottom", "1px dashed var(--line-2)") } }
            ) { Text("~/blog") }
            Span(attrs = { style { property("color", "var(--fg-faint)") } }) { Text(" → ") }
            Text("전체 글 보기 · ${blogPosts.size} posts")
        }
    }
}
