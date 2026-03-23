package com.kongjak.kongjak.pages.blog

import androidx.compose.runtime.Composable
import com.kongjak.kongjak.components.sections.blog.BlogListing
import com.kongjak.kongjak.components.layouts.PageLayoutData
import com.kongjak.kongjak.generated.blogPosts
import com.kongjak.kongjak.toSitePalette
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px

@InitRoute
fun initBlogPage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Blog"))
}

val BlogHeroLabelStyle = CssStyle.base {
    Modifier
        .fontSize(0.85.cssRem)
        .fontWeight(FontWeight.Bold)
        .color(colorMode.toSitePalette().accent)
        .letterSpacing(0.18.em)
        .textAlign(TextAlign.Center)
}

val BlogCardStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .padding(28.px)
            .borderRadius(18.px)
            .backgroundColor(colorMode.toSitePalette().cardBackground)
            .border(1.px, LineStyle.Solid, colorMode.toSitePalette().divider)
            .textDecorationLine(TextDecorationLine.None)
    }
    hover {
        Modifier.border(1.px, LineStyle.Solid, colorMode.toSitePalette().accent)
    }
}

@Page("/blog")
@Layout(".components.layouts.PageLayout")
@Composable
fun BlogPage() {
    BlogListing(
        pageContext = rememberPageContext(),
        posts = blogPosts,
        heading = "전체 게시글",
        description = ""
    )
}
