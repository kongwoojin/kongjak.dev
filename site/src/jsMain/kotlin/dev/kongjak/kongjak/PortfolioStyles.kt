package dev.kongjak.kongjak

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.StyleScope
import org.jetbrains.compose.web.css.CSSMediaQuery

@InitSilk
fun initPortfolioStyles(ctx: InitSilkContext) {
    ctx.foundation()
    ctx.navigation()
    ctx.introduction()
    ctx.projectCards()
    ctx.skillsAndExperience()
    ctx.blogAndFooter()
    ctx.responsive()
}

private fun InitSilkContext.foundation() {
    props(":root",
        "--bg" to "#ffffff", "--card" to "#ffffff", "--soft" to "#f6f7f8",
        "--fg" to "#202124", "--muted" to "#666b70", "--line" to "#e4e6e8",
        "--accent" to "#315d91", "--max" to "1080px",
        "--font-sans" to "'Pretendard Variable', 'Pretendard', -apple-system, BlinkMacSystemFont, system-ui, sans-serif",
        "--font-display" to "'Paperlogy', 'Pretendard Variable', sans-serif",
        "--font-brand" to "'Gmarket Sans', 'Pretendard Variable', sans-serif",
        "--font-mono" to "'JetBrains Mono', monospace")
    props(":root[data-theme=\"dark\"]",
        "--bg" to "#17191b", "--card" to "#1c1e20", "--soft" to "#24272a",
        "--fg" to "#f1f2f3", "--muted" to "#a9adb1", "--line" to "#34383c",
        "--accent" to "#8aadd4")
    props("*", "box-sizing" to "border-box")
    props("html", "scroll-behavior" to "smooth", "scroll-padding-top" to "100px")
    props("html, body", "margin" to "0", "padding" to "0")
    props("body", "background" to "var(--bg)", "color" to "var(--fg)",
        "font-family" to "var(--font-sans)", "font-size" to "15px", "line-height" to "1.7",
        "-webkit-font-smoothing" to "antialiased", "word-break" to "keep-all", "overflow-wrap" to "break-word")
    props("h1, h2, h3, p", "margin" to "0")
    props("a", "color" to "inherit", "text-decoration" to "none")
    props("button", "font" to "inherit")
    props("a, button", "-webkit-tap-highlight-color" to "transparent")
    props("a:focus-visible, button:focus-visible", "outline" to "2px solid var(--accent)", "outline-offset" to "5px")
    props("::selection", "background" to "#dce8f4", "color" to "#202124")
    props(".shell", "max-width" to "var(--max)", "padding" to "0 32px", "margin" to "0 auto")
    props(".portfolio-section", "padding" to "76px 0 82px", "border-top" to "1px solid var(--line)")
    props(".section-heading", "display" to "flex", "align-items" to "baseline",
        "justify-content" to "space-between", "gap" to "24px", "margin-bottom" to "24px")
    props(".section-heading h2", "font-family" to "var(--font-display)", "font-size" to "27px", "font-weight" to "600", "letter-spacing" to "-.025em", "line-height" to "1.35")
    props(".section-heading > p", "font-size" to "14px", "color" to "var(--muted)")
    props(".small-label", "font-size" to "12px", "font-weight" to "500", "color" to "var(--muted)", "letter-spacing" to ".015em")
    props(".quiet-link", "display" to "inline-block", "font-size" to "14px", "font-weight" to "600", "transition" to "color .15s")
    props(".quiet-link:hover", "color" to "var(--accent)")
}

private fun InitSilkContext.navigation() {
    props(".topbar", "background" to "var(--bg)", "border-bottom" to "1px solid var(--line)")
    props(".topbar-inner", "max-width" to "var(--max)", "padding" to "0 32px", "height" to "68px",
        "margin" to "0 auto", "display" to "flex", "align-items" to "center")
    props(".brand", "font-family" to "var(--font-brand)", "font-size" to "20px", "font-weight" to "700", "letter-spacing" to "-.025em", "line-height" to "1")
    props(".brand span", "color" to "var(--accent)")
    props(".nav", "margin-left" to "auto", "display" to "flex", "gap" to "25px", "align-items" to "center")
    props(".nav a", "font-size" to "13px", "color" to "var(--muted)", "font-weight" to "500", "transition" to "color .15s")
    props(".nav a:hover", "color" to "var(--fg)")
    props(".theme-toggle", "display" to "inline-flex", "align-items" to "center", "justify-content" to "center",
        "width" to "32px", "height" to "32px", "margin-left" to "22px", "border" to "1px solid var(--line)",
        "border-radius" to "4px", "background" to "transparent", "color" to "var(--muted)", "cursor" to "pointer", "font-size" to "12px")
    props(".theme-toggle:hover", "background" to "var(--soft)", "color" to "var(--fg)")
}

private fun InitSilkContext.introduction() {
    props(".hero", "padding" to "104px 0 94px")
    props(".eyebrow", "font-size" to "15px", "color" to "var(--muted)", "margin-bottom" to "8px")
    props(".hero h1", "font-family" to "var(--font-brand)", "font-size" to "clamp(42px, 5vw, 58px)", "font-weight" to "700",
        "letter-spacing" to "-.035em", "line-height" to "1.25", "margin-bottom" to "15px")
    props(".hero h1 span", "font-weight" to "700")
    props(".hero-description", "max-width" to "650px", "font-size" to "15px", "color" to "var(--muted)", "line-height" to "1.8")
    props(".hero-links", "display" to "flex", "align-items" to "center", "gap" to "19px", "margin-top" to "23px", "flex-wrap" to "wrap")
}

private fun InitSilkContext.projectCards() {
    props(".featured-work-grid", "display" to "grid", "grid-template-columns" to "repeat(2, minmax(0, 1fr))", "gap" to "16px", "margin-bottom" to "16px")
    props(".featured-work-card", "display" to "grid", "grid-template-columns" to "minmax(0, .9fr) minmax(0, 1.1fr)",
        "border" to "1px solid var(--line)", "border-radius" to "5px", "overflow" to "hidden", "background" to "var(--card)", "min-width" to "0")
    props(".feature-visual", "position" to "relative", "display" to "flex", "align-items" to "center", "justify-content" to "center",
        "min-width" to "0", "min-height" to "360px", "padding" to "14px", "background" to "var(--soft)")
    props(".feature-image", "display" to "block", "width" to "auto", "height" to "330px", "max-width" to "100%", "object-fit" to "contain")
    props(".slider-controls", "position" to "absolute", "right" to "10px", "bottom" to "10px", "display" to "flex",
        "align-items" to "center", "gap" to "2px", "padding" to "3px", "border" to "1px solid var(--line)",
        "border-radius" to "4px", "background" to "var(--card)", "color" to "var(--fg)")
    props(".slider-button", "width" to "30px", "height" to "30px", "border" to "0", "background" to "transparent",
        "color" to "inherit", "cursor" to "pointer", "font-size" to "15px")
    props(".slider-button:hover", "color" to "var(--accent)")
    props(".slider-count", "min-width" to "34px", "text-align" to "center", "font-size" to "11px", "color" to "var(--muted)")
    props(".featured-work-copy", "display" to "flex", "flex-direction" to "column", "padding" to "22px", "min-width" to "0")
    props(".featured-work-copy h3", "font-family" to "var(--font-display)", "font-size" to "21px", "font-weight" to "600", "line-height" to "1.4")
    props(".featured-work-copy .work-description", "margin" to "14px 0 18px", "font-size" to "13px")
    props(".work-grid", "display" to "grid", "grid-template-columns" to "repeat(2, minmax(0, 1fr))", "gap" to "16px")
    props(".work-card", "border" to "1px solid var(--line)", "border-radius" to "5px",
        "background" to "var(--card)", "min-width" to "0")
    props(".work-body", "padding" to "22px", "display" to "flex", "flex-direction" to "column", "min-height" to "220px")
    props(".work-category", "font-size" to "12px", "color" to "var(--muted)", "margin-bottom" to "8px")
    props(".work-body h3", "font-family" to "var(--font-display)", "font-size" to "20px", "font-weight" to "600", "letter-spacing" to "-.02em", "line-height" to "1.4")
    props(".work-body h3 a:hover", "color" to "var(--accent)")
    props(".work-role", "font-size" to "12px", "color" to "var(--muted)", "margin-top" to "5px", "line-height" to "1.6")
    props(".work-description", "font-size" to "14px", "color" to "var(--muted)", "line-height" to "1.75", "margin" to "13px 0 19px")
    props(".project-tags, .skill-tags", "display" to "flex", "flex-wrap" to "wrap", "gap" to "6px",
        "padding" to "0", "margin" to "0", "list-style" to "none")
    props(".project-tags", "margin-top" to "auto", "padding-top" to "2px")
    props(".project-chip", "display" to "inline-flex", "align-items" to "center", "min-height" to "26px",
        "padding" to "3px 9px", "border" to "1px solid var(--line)", "border-radius" to "999px",
        "background" to "var(--soft)", "color" to "var(--muted)", "font-size" to "10px", "line-height" to "1.3",
        "white-space" to "nowrap")
    props(".section-end", "display" to "flex", "align-items" to "center", "justify-content" to "flex-end",
        "gap" to "20px", "margin-top" to "26px", "font-size" to "12px", "color" to "var(--muted)")
    props(".section-end .quiet-link", "font-size" to "12px", "white-space" to "nowrap", "color" to "var(--fg)")
}

private fun InitSilkContext.skillsAndExperience() {
    props(".skill-tags", "display" to "flex", "flex-wrap" to "wrap", "align-items" to "center", "gap" to "10px",
        "padding" to "0", "margin" to "0", "list-style" to "none")
    props(".skill-chip", "display" to "inline-flex", "align-items" to "center", "padding" to "5px 11px",
        "border" to "1px solid #d2d7dc", "border-radius" to "999px", "background" to "#f2f4f6",
        "color" to "#41464b", "font-size" to "12px", "line-height" to "1.3", "white-space" to "nowrap")
    props(":root[data-theme=\"dark\"] .skill-chip", "border-color" to "#454a50", "background" to "#25292d", "color" to "#e1e4e7")
    props(".about-grid", "display" to "grid", "grid-template-columns" to "1fr 1.15fr", "gap" to "90px")
    props(".about-copy h3", "font-size" to "23px", "font-weight" to "600", "letter-spacing" to "-.04em", "line-height" to "1.55", "margin" to "3px 0 22px")
    props(".about-copy p", "font-size" to "14px", "color" to "var(--muted)", "line-height" to "1.9", "margin-top" to "13px", "letter-spacing" to "-.02em")
    props(".experience-row", "display" to "grid", "grid-template-columns" to "120px 1fr", "gap" to "16px",
        "padding" to "16px 0", "border-bottom" to "1px solid var(--line)")
    props(".experience-row:first-child", "padding-top" to "2px")
    props(".experience-row:last-child", "border-bottom" to "0")
    props(".experience-period", "font-family" to "var(--font-mono)", "font-size" to "10px", "color" to "var(--muted)", "padding-top" to "3px")
    props(".experience-row h3", "font-size" to "14px", "font-weight" to "600", "line-height" to "1.6")
    props(".experience-row p", "font-size" to "11px", "color" to "var(--muted)", "margin-top" to "3px")
    props(".opensource-row", "display" to "grid", "grid-template-columns" to "1fr 1.15fr", "gap" to "90px",
        "border-top" to "1px solid var(--line)", "margin-top" to "34px", "padding-top" to "26px")
    props(".contribution-link", "display" to "flex", "gap" to "14px", "align-items" to "baseline", "font-size" to "12px", "margin-bottom" to "12px")
    props(".contribution-link span:first-child", "font-weight" to "600", "white-space" to "nowrap")
    props(".contribution-link span:nth-child(2)", "font-size" to "11px", "color" to "var(--muted)")
    props(".contribution-link span:last-child", "margin-left" to "auto")
    props(".contribution-link:hover", "color" to "var(--accent)")
}

private fun InitSilkContext.blogAndFooter() {
    props(".blog-list", "border-top" to "1px solid var(--line)")
    props(".blog-entry", "display" to "flex", "align-items" to "center", "justify-content" to "space-between",
        "gap" to "24px", "padding" to "21px 2px", "border-bottom" to "1px solid var(--line)",
        "transition" to "color .15s")
    props(".blog-entry:hover", "color" to "var(--accent)")
    props(".blog-entry-copy", "display" to "flex", "flex-direction" to "column", "gap" to "7px", "min-width" to "0")
    props(".blog-entry h3", "font-family" to "var(--font-display)", "font-size" to "18px", "font-weight" to "500", "letter-spacing" to "-.025em", "line-height" to "1.5")
    props(".blog-meta", "font-size" to "12px", "color" to "var(--muted)")
    props(".blog-entry-arrow", "flex-shrink" to "0", "font-size" to "18px", "color" to "var(--muted)")
    props(".site-footer", "padding" to "43px 0 26px", "border-top" to "1px solid var(--line)")
    props(".footer-email", "font-size" to "18px", "font-weight" to "600", "letter-spacing" to "-.025em")
    props(".footer-email:hover", "color" to "var(--accent)")
    props(".site-footer > div > p", "font-size" to "12px", "color" to "var(--muted)", "margin-top" to "7px")
    props(".footer-bottom", "display" to "flex", "justify-content" to "space-between", "font-size" to "11px", "color" to "var(--muted)", "margin-top" to "38px")
    props(".footer-bottom > div", "display" to "flex", "gap" to "24px")
}

private fun InitSilkContext.responsive() {
    media(".featured-work-grid", "(max-width: 900px)", "grid-template-columns" to "1fr")
    media(".featured-work-card", "(max-width: 900px)", "grid-template-columns" to "250px minmax(0, 1fr)")
    media(".feature-visual", "(max-width: 900px)", "min-height" to "380px")
    media(".work-grid", "(max-width: 900px)", "gap" to "14px")
    media(".about-grid, .opensource-row", "(max-width: 900px)", "gap" to "38px", "grid-template-columns" to "1fr 1fr")
    media(".section-heading", "(max-width: 640px)", "align-items" to "flex-start", "flex-direction" to "column", "gap" to "7px", "margin-bottom" to "20px")
    media(".section-heading h2", "(max-width: 640px)", "font-size" to "25px")
    media(".section-heading > p", "(max-width: 640px)", "font-size" to "12px")
    media(".shell, .topbar-inner", "(max-width: 640px)", "padding-left" to "22px", "padding-right" to "22px")
    media(".topbar-inner", "(max-width: 640px)", "height" to "68px")
    media(".brand", "(max-width: 640px)", "font-size" to "22px")
    media(".nav", "(max-width: 640px)", "gap" to "17px")
    media(".nav a", "(max-width: 640px)", "font-size" to "11px")
    media(".nav a:nth-child(3)", "(max-width: 640px)", "display" to "none")
    media(".theme-toggle", "(max-width: 640px)", "margin-left" to "17px", "width" to "29px", "height" to "29px", "font-size" to "11px")
    media(".hero", "(max-width: 640px)", "padding" to "67px 0 60px")
    media(".hero h1", "(max-width: 640px)", "font-size" to "clamp(36px, 8vw, 48px)", "margin-bottom" to "15px")
    media(".eyebrow", "(max-width: 640px)", "font-size" to "13px", "margin-bottom" to "7px")
    media(".hero-description", "(max-width: 640px)", "font-size" to "13px", "line-height" to "1.8")
    media(".hero-links", "(max-width: 640px)", "gap" to "18px", "margin-top" to "20px")
    media(".hero-links .quiet-link", "(max-width: 640px)", "font-size" to "12px")
    media(".portfolio-section", "(max-width: 640px)", "padding" to "47px 0 50px")
    media(".work-grid", "(max-width: 640px)", "grid-template-columns" to "1fr", "gap" to "20px")
    media(".featured-work-card", "(max-width: 640px)", "grid-template-columns" to "1fr")
    media(".feature-visual", "(max-width: 640px)", "min-height" to "360px")
    media(".feature-image", "(max-width: 640px)", "height" to "330px")
    media(".featured-work-copy", "(max-width: 640px)", "padding" to "19px")
    media(".work-body", "(max-width: 640px)", "padding" to "18px", "min-height" to "0")
    media(".section-end", "(max-width: 640px)", "align-items" to "flex-start", "flex-direction" to "column", "gap" to "9px")
    media(".about-grid", "(max-width: 640px)", "grid-template-columns" to "1fr", "gap" to "30px")
    media(".about-copy h3", "(max-width: 640px)", "font-size" to "21px")
    media(".about-copy p", "(max-width: 640px)", "font-size" to "13px")
    media(".experience-row", "(max-width: 640px)", "grid-template-columns" to "103px 1fr", "gap" to "12px")
    media(".experience-row h3", "(max-width: 640px)", "font-size" to "13px")
    media(".experience-period", "(max-width: 640px)", "font-size" to "9px")
    media(".opensource-row", "(max-width: 640px)", "grid-template-columns" to "1fr", "gap" to "18px", "margin-top" to "23px", "padding-top" to "22px")
    media(".contribution-link", "(max-width: 640px)", "gap" to "12px", "font-size" to "11px")
    media(".contribution-link span:nth-child(2)", "(max-width: 640px)", "font-size" to "10px")
    media(".blog-entry", "(max-width: 640px)", "gap" to "14px", "padding" to "17px 1px")
    media(".blog-entry h3", "(max-width: 640px)", "font-size" to "16px")
    media(".blog-meta", "(max-width: 640px)", "font-size" to "11px")
    media(".site-footer", "(max-width: 640px)", "padding-top" to "31px")
    media(".footer-email", "(max-width: 640px)", "font-size" to "16px")
    media(".site-footer > div > p", "(max-width: 640px)", "font-size" to "11px")
    media(".footer-bottom", "(max-width: 640px)", "margin-top" to "30px")
    media(".nav", "(max-width: 360px)", "gap" to "12px")
    media(".theme-toggle", "(max-width: 360px)", "margin-left" to "12px")
    media("html", "(prefers-reduced-motion: reduce)", "scroll-behavior" to "auto")
    media(".quiet-link, .nav a", "(prefers-reduced-motion: reduce)", "transition" to "none")
}

private fun InitSilkContext.props(selector: String, vararg props: Pair<String, String>) {
    stylesheet.registerStyleBase(selector) {
        Modifier.styleModifier { props.forEach { (key, value) -> property(key, value) } }
    }
}

private fun InitSilkContext.media(selector: String, query: String, vararg props: Pair<String, String>) {
    stylesheet.registerStyle(selector) {
        cssRule(CSSMediaQuery.Raw(query)) {
            Modifier.styleModifier { props.forEach { (key, value) -> property(key, value) } }
        }
    }
}
