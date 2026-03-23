package com.kongjak.kongjak

import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.silk.components.forms.ButtonStyle
import com.varabyte.kobweb.silk.components.forms.ButtonVars
import com.varabyte.kobweb.silk.components.layout.HorizontalDividerStyle
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.addVariantBase
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.theme.colors.palette.color
import com.varabyte.kobweb.silk.theme.colors.palette.toPalette
import com.varabyte.kobweb.silk.theme.modifyStyleBase
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.CSSMediaQuery
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.StylePropertyValue
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@InitSilk
fun initSiteStyles(ctx: InitSilkContext) {
    // This site does not need scrolling itself, but this is a good demonstration for how you might enable this in your
    // own site. Note that we only enable smooth scrolling unless the user has requested reduced motion, which is
    // considered a best practice.
    ctx.stylesheet.registerStyle("html") {
        cssRule(CSSMediaQuery.MediaFeature("prefers-reduced-motion", StylePropertyValue("no-preference"))) {
            Modifier.scrollBehavior(ScrollBehavior.Smooth)
        }
    }

    ctx.stylesheet.registerStyleBase("body") {
        Modifier
            .fontFamily(
                "Pretendard Variable",
                "Pretendard",
                "-apple-system",
                "BlinkMacSystemFont",
                "Segoe UI",
                "Roboto",
                "Oxygen",
                "Ubuntu",
                "Cantarell",
                "Fira Sans",
                "Droid Sans",
                "Helvetica Neue",
                "sans-serif"
            )
            .fontSize(18.px)
            .lineHeight(1.5)
            .margin(0.px)
            .padding(0.px)
    }

    ctx.stylesheet.registerStyleBase("html") {
        Modifier
            .margin(0.px)
            .padding(0.px)
    }

    ctx.stylesheet.registerStyleBase(".blog-article h1, .blog-article h2, .blog-article h3") {
        Modifier.lineHeight(1.2)
    }

    ctx.stylesheet.registerStyleBase(".blog-article p, .blog-article li") {
        Modifier.lineHeight(1.8)
    }

    ctx.stylesheet.registerStyleBase(".blog-article pre, .blog-article code") {
        Modifier
            .fontFamily("JetBrains Mono", "Fira Code", "SFMono-Regular", "monospace")
            .borderRadius(8.px)
    }

    ctx.stylesheet.registerStyleBase(".blog-article pre") {
        Modifier
            .fillMaxWidth()
            .margin(topBottom = 24.px, leftRight = 0.px)
            .padding(18.px)
            .border(1.px, LineStyle.Solid, Colors.DarkGray)
    }

    ctx.stylesheet.registerStyleBase(".blog-article pre code") {
        Modifier
            .padding(0.px)
    }

    ctx.stylesheet.registerStyleBase(".blog-article :not(pre) > code") {
        Modifier
            .padding(leftRight = 6.px, topBottom = 2.px)
            .border(1.px, LineStyle.Solid, Colors.Gray)
    }

    ctx.stylesheet.registerStyleBase(".blog-article img") {
        Modifier.fillMaxWidth().borderRadius(12.px)
    }

    ctx.stylesheet.registerStyleBase(".blog-article blockquote") {
        Modifier
            .margin(topBottom = 24.px, leftRight = 0.px)
            .padding(left = 16.px)
    }

    ctx.stylesheet.registerStyle(".blog-post-header") {
        base {
            Modifier.padding(bottom = 0.75.cssRem)
        }
    }

    ctx.stylesheet.registerStyle(".blog-post-meta") {
        base {
            Modifier.fontSize(0.92.cssRem).lineHeight(1.6)
        }
    }

    ctx.stylesheet.registerStyle(".blog-post-tags") {
        base {
            Modifier.padding(top = 0.5.cssRem)
        }
    }

    ctx.stylesheet.registerStyleBase(".blog-sidebar") {
        Modifier
            .position(Position.Sticky)
            .top(6.cssRem)
    }

    ctx.stylesheet.registerStyleBase(".blog-toc") {
        Modifier
            .position(Position.Sticky)
            .top(6.cssRem)
    }

    ctx.theme.modifyStyleBase(HorizontalDividerStyle) {
        Modifier.fillMaxWidth()
    }
}

val HeadlineTextStyle = CssStyle.base {
    Modifier
        .fontSize(3.cssRem)
        .textAlign(TextAlign.Start)
        .lineHeight(1.2) //1.5x doesn't look as good on very large text
}

val SubheadlineTextStyle = CssStyle.base {
    Modifier
        .fontSize(1.cssRem)
        .textAlign(TextAlign.Start)
        .color(colorMode.toPalette().color.toRgb().copyf(alpha = 0.8f))
}

val CircleButtonVariant = ButtonStyle.addVariantBase {
    Modifier.padding(0.px).borderRadius(50.percent)
}

val UncoloredButtonVariant = ButtonStyle.addVariantBase {
    Modifier.setVariable(ButtonVars.BackgroundDefaultColor, Colors.Transparent)
}
