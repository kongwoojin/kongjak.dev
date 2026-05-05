package com.kongjak.kongjak.pages.blog

import androidx.compose.runtime.Composable
import com.kongjak.kongjak.components.layouts.PageLayoutData
import com.kongjak.kongjak.components.sections.CrumbPart
import com.kongjak.kongjak.components.sections.blog.BlogListing
import com.kongjak.kongjak.generated.blogPosts
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.core.rememberPageContext

@InitRoute
fun initBlogPage(ctx: InitRouteContext) {
    ctx.data.add(
        PageLayoutData(
            title = "Blog",
            crumb = listOf(CrumbPart.Active("blog")),
        )
    )
}

@Page("/blog")
@Layout(".components.layouts.PageLayout")
@Composable
fun BlogPage() {
    BlogListing(
        pageContext = rememberPageContext(),
        posts = blogPosts,
        heading = "blog",
        description = "",
    )
}
