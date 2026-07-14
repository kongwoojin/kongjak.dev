package dev.kongjak.kongjak

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.opacity
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.StyleScope
import org.jetbrains.compose.web.css.CSSMediaQuery
import org.jetbrains.compose.web.css.percent

// Compact, verbatim port of the previous portfolio.css into Kobweb's @InitSilk
// stylesheet API. Keeps using string `var(--…)` references — switching to
// Kobweb StyleVariables would touch every property and is out of scope here.

@InitSilk
fun initPortfolioStyles(ctx: InitSilkContext) {
    ctx.tokens()
    ctx.tweakPalettes()
    ctx.densityVariants()
    ctx.textureVariants()
    ctx.baseElements()
    ctx.layoutShellAndTopbar()
    ctx.crumb()
    ctx.sectionPrimitives()
    ctx.hero()
    ctx.skills()
    ctx.projects()
    ctx.contributions()
    ctx.contact()
    ctx.footer()
    ctx.revealAnimation()
    ctx.responsiveTablet()
    ctx.responsiveMobile()
    ctx.responsiveSmallMobile()
    ctx.tweaksPanel()
    ctx.productRedesign()
    ctx.caseStudyStyles()
}

private fun InitSilkContext.productRedesign() {
    props(":root",
        "--bg" to "#f7f8fa", "--bg-soft" to "#eef1f3", "--bg-card" to "#ffffff",
        "--line" to "#dce1e5", "--line-2" to "#bdc6cc", "--fg" to "#18201d",
        "--fg-soft" to "#4b5752", "--fg-mute" to "#75817b", "--fg-faint" to "#aeb8b3",
        "--accent" to "#087f5b", "--accent-2" to "#ef6c47", "--accent-dim" to "#dcefe8",
        "--grid" to "rgba(24,32,29,.035)", "--max" to "1080px", "--gutter" to "32px",
        "--font-sans" to "\"SUIT Variable\", \"Pretendard Variable\", -apple-system, BlinkMacSystemFont, system-ui, sans-serif")
    props(":root[data-theme=\"dark\"]",
        "--bg" to "#111513", "--bg-soft" to "#1a201d", "--bg-card" to "#171c19",
        "--line" to "#2b332f", "--line-2" to "#46514c", "--fg" to "#eef4f0",
        "--fg-soft" to "#b3beb8", "--fg-mute" to "#849089", "--fg-faint" to "#535e58",
        "--accent" to "#4bd0a0", "--accent-2" to "#ff8a68", "--accent-dim" to "#183d31",
        "--grid" to "rgba(255,255,255,.03)")
    props("body", "font-size" to "15px", "line-height" to "1.65", "letter-spacing" to "0")
    props("body::before", "background-image" to "radial-gradient(var(--grid) 1px, transparent 1px)",
        "background-size" to "24px 24px", "mask-image" to "linear-gradient(to bottom, black, transparent 75%)",
        "-webkit-mask-image" to "linear-gradient(to bottom, black, transparent 75%)", "opacity" to ".7")
    props(".shell", "max-width" to "var(--max)", "padding" to "0 var(--gutter)")

    props(".topbar", "background" to "color-mix(in oklab, var(--bg) 88%, transparent)",
        "border-bottom" to "1px solid var(--line)", "backdrop-filter" to "blur(16px)")
    props(".topbar-inner", "max-width" to "var(--max)", "padding" to "14px var(--gutter)",
        "font-family" to "var(--font-sans)", "font-size" to "13px")
    props(".brand", "gap" to "9px", "font-size" to "14px", "letter-spacing" to "0")
    props(".brand .dot", "display" to "block", "width" to "8px", "height" to "8px")
    props(".brand span", "color" to "var(--fg-mute)")
    props(".nav", "gap" to "6px")
    props(".nav a", "padding" to "6px 9px", "border-radius" to "5px")
    props(".nav a:hover", "background" to "var(--bg-soft)")
    props(".theme-toggle", "width" to "32px", "height" to "32px", "border-radius" to "6px")

    props("section", "padding" to "82px 0")
    props(".hero", "min-height" to "auto", "padding" to "64px 0 72px", "display" to "grid",
        "grid-template-columns" to "minmax(0, 1.08fr) minmax(300px, .72fr)", "align-items" to "center", "gap" to "72px")
    props(".hero-copy", "min-width" to "0")
    props(".hero-kicker", "display" to "flex", "align-items" to "center", "gap" to "9px",
        "margin-bottom" to "24px", "font-family" to "var(--font-mono)", "font-size" to "11px",
        "color" to "var(--fg-mute)", "text-transform" to "uppercase")
    props(".status-dot", "width" to "8px", "height" to "8px", "border-radius" to "50%", "background" to "var(--accent)",
        "box-shadow" to "0 0 0 4px color-mix(in oklab, var(--accent) 15%, transparent)")
    props(".hero h1", "margin" to "0 0 26px", "font-size" to "clamp(48px, 7vw, 76px)", "line-height" to "1",
        "font-weight" to "760", "letter-spacing" to "0", "color" to "var(--fg)")
    props(".hero-lead", "margin" to "0", "max-width" to "16ch", "font-size" to "clamp(24px, 3vw, 34px)",
        "line-height" to "1.35", "color" to "var(--fg)")
    props(".hero-lead b", "font-weight" to "650", "color" to "var(--accent)")
    props(".hero-about", "max-width" to "54ch", "margin" to "0", "color" to "var(--fg-soft)", "font-size" to "17px", "line-height" to "1.75")
    props(".hero-actions", "display" to "flex", "align-items" to "center", "gap" to "22px", "margin-top" to "30px")
    props(".primary-action", "display" to "inline-flex", "padding" to "11px 16px", "border-radius" to "6px",
        "background" to "var(--accent)", "color" to "#fff", "font-weight" to "600", "transition" to "transform .15s, opacity .15s")
    props(".primary-action:hover", "transform" to "translateY(-1px)", "opacity" to ".9")
    props(".text-action", "font-weight" to "600", "color" to "var(--fg-soft)", "border-bottom" to "1px solid var(--line-2)")
    props(".hero-proof", "display" to "grid", "grid-template-columns" to "repeat(2, minmax(0, 1fr))", "gap" to "20px",
        "max-width" to "430px", "margin-top" to "42px", "padding-top" to "20px", "border-top" to "1px solid var(--line)")
    props(".hero-proof > div", "display" to "flex", "flex-direction" to "column", "gap" to "1px")
    props(".hero-proof b", "font-size" to "19px", "font-weight" to "650")
    props(".hero-proof span", "font-size" to "11px", "color" to "var(--fg-mute)")
    props(".hero-product", "align-self" to "stretch", "display" to "flex", "flex-direction" to "column", "min-height" to "470px",
        "padding" to "18px", "overflow" to "hidden", "border" to "1px solid var(--line)", "border-radius" to "8px",
        "background" to "var(--bg-card)", "box-shadow" to "0 22px 60px rgba(25, 42, 35, .09)")
    props(".product-caption", "display" to "flex", "justify-content" to "space-between", "gap" to "16px", "padding" to "2px 2px 16px")
    props(".product-caption > div", "display" to "flex", "flex-direction" to "column")
    props(".product-caption span", "font-family" to "var(--font-mono)", "font-size" to "9px", "color" to "var(--fg-mute)")
    props(".product-caption b", "font-size" to "13px", "font-weight" to "600")
    props(".phone-shot", "position" to "relative", "flex" to "1", "overflow" to "hidden", "border-radius" to "6px",
        "background" to "#0a2c3c")
    props(".phone-shot img", "position" to "absolute", "width" to "100%", "height" to "auto", "top" to "0", "left" to "0",
        "display" to "block")
    props(".scope-head", "display" to "flex", "flex-direction" to "column", "gap" to "5px", "padding" to "4px 2px 20px")
    props(".scope-head span", "font-family" to "var(--font-mono)", "font-size" to "9px", "color" to "var(--accent)")
    props(".scope-head b", "font-size" to "17px", "font-weight" to "650")
    props(".scope-list", "display" to "flex", "flex" to "1", "flex-direction" to "column", "justify-content" to "center",
        "border-top" to "1px solid var(--line)")
    props(".scope-row", "display" to "grid", "grid-template-columns" to "34px 1fr", "gap" to "12px", "padding" to "19px 2px",
        "border-bottom" to "1px solid var(--line)")
    props(".scope-row > span", "padding-top" to "3px", "font-family" to "var(--font-mono)", "font-size" to "9px", "color" to "var(--fg-faint)")
    props(".scope-row b", "font-size" to "15px", "font-weight" to "650")
    props(".scope-row p", "margin" to "2px 0 0", "font-size" to "12px", "line-height" to "1.5", "color" to "var(--fg-mute)")

    props(".section-head", "display" to "grid", "grid-template-columns" to "42px auto 1fr", "gap" to "14px",
        "align-items" to "center", "margin-bottom" to "32px")
    props(".section-head .num", "font-size" to "10px", "color" to "var(--accent)")
    props(".section-head h2", "font-family" to "var(--font-sans)", "font-size" to "24px", "font-weight" to "650",
        "letter-spacing" to "0", "text-transform" to "none")
    props(".section-head .rule", "display" to "block")
    props(".skills-grid", "grid-template-columns" to "repeat(3, 1fr)", "gap" to "12px")
    props(".skill-group", "padding" to "20px", "border" to "1px solid var(--line)", "border-radius" to "7px", "background" to "var(--bg-card)")
    props(".skill-group h3", "font-size" to "11px", "color" to "var(--accent)")
    props(".skill-list li", "padding" to "9px 0", "font-size" to "14px")
    props(".projects", "gap" to "10px", "border-top" to "0")
    props(".project", "border" to "1px solid var(--line)", "border-radius" to "7px", "background" to "var(--bg-card)")
    props(".p-head", "padding" to "18px 20px", "grid-template-columns" to "42px 1fr 28px")
    props(".p-title", "font-size" to "20px", "font-weight" to "650")
    props(".p-body-inner", "padding" to "0 20px 22px 62px", "display" to "grid", "grid-template-columns" to "1fr", "gap" to "18px")
    props(".p-desc", "max-width" to "68ch", "font-size" to "15px")
    props(".p-mod", "border-radius" to "5px")
    props(".contrib-card, .contact-block", "border-radius" to "7px", "background" to "var(--bg-card)")
    props(".contact-tabbar", "background" to "var(--bg-soft)", "color" to "var(--fg-soft)")
    props("footer", "padding" to "28px 0 40px")
    props(".twk-root", "display" to "none")

    style(":root") { cssMedia("(max-width: 720px)", "--gutter" to "20px") }
    style(".nav") { cssMedia("(max-width: 720px)", "display" to "none") }
    style("section") { cssMedia("(max-width: 720px)", "padding" to "58px 0") }
    style(".hero") { cssMedia("(max-width: 720px)", "padding" to "42px 0 58px", "grid-template-columns" to "1fr", "gap" to "42px") }
    style(".hero h1") { cssMedia("(max-width: 720px)", "font-size" to "48px", "margin-bottom" to "18px") }
    style(".hero-lead") { cssMedia("(max-width: 720px)", "font-size" to "26px") }
    style(".hero-about") { cssMedia("(max-width: 720px)", "font-size" to "15px") }
    style(".hero-product") { cssMedia("(max-width: 720px)", "min-height" to "460px", "padding" to "14px") }
    style(".skills-grid") { cssMedia("(max-width: 720px)", "grid-template-columns" to "1fr") }
    style(".section-head") { cssMedia("(max-width: 720px)", "grid-template-columns" to "32px auto 1fr") }
    style(".p-head") { cssMedia("(max-width: 720px)", "padding" to "16px", "grid-template-columns" to "32px 1fr 24px") }
    style(".p-body-inner") { cssMedia("(max-width: 720px)", "padding" to "0 16px 20px") }
}

private fun InitSilkContext.caseStudyStyles() {
    props(".section-intro", "display" to "grid", "grid-template-columns" to "170px minmax(0, 1fr)", "gap" to "12px 36px",
        "align-items" to "start", "margin-bottom" to "44px")
    props(".section-intro > span", "padding-top" to "8px", "font-family" to "var(--font-mono)", "font-size" to "10px",
        "font-weight" to "600", "color" to "var(--accent)", "letter-spacing" to ".08em")
    props(".section-intro h2", "max-width" to "760px", "margin" to "0", "font-size" to "clamp(30px, 4vw, 48px)",
        "line-height" to "1.22", "font-weight" to "680", "letter-spacing" to "0")
    props(".section-intro > p", "grid-column" to "2", "max-width" to "680px", "margin" to "6px 0 0", "font-size" to "16px", "color" to "var(--fg-soft)")
    props(".section-intro.compact h2", "font-size" to "clamp(28px, 3.4vw, 40px)")

    props(".impact-metrics", "display" to "grid", "grid-template-columns" to "repeat(3, 1fr)", "margin-bottom" to "28px",
        "border-top" to "1px solid var(--line)", "border-bottom" to "1px solid var(--line)")
    props(".impact-metric", "padding" to "28px 28px 28px 0", "border-right" to "1px solid var(--line)")
    props(".impact-metric + .impact-metric", "padding-left" to "28px")
    props(".impact-metric:last-child", "border-right" to "0")
    props(".impact-metric > b", "display" to "block", "margin-bottom" to "8px", "font-size" to "clamp(38px, 5vw, 58px)",
        "line-height" to "1", "font-weight" to "720", "color" to "var(--accent)")
    props(".impact-metric h3", "margin" to "0 0 8px", "font-size" to "16px")
    props(".impact-metric p", "margin" to "0", "font-size" to "13px", "color" to "var(--fg-mute)")

    props(".case-study", "display" to "grid", "grid-template-columns" to "1.08fr .72fr", "min-height" to "520px",
        "overflow" to "hidden", "border" to "1px solid var(--line)", "border-radius" to "8px", "background" to "var(--bg-card)")
    props(".case-copy", "display" to "flex", "flex-direction" to "column", "justify-content" to "center", "padding" to "48px")
    props(".case-label", "margin-bottom" to "20px", "font-family" to "var(--font-mono)", "font-size" to "10px", "color" to "var(--accent-2)")
    props(".case-copy h3", "max-width" to "15ch", "margin" to "0 0 22px", "font-size" to "clamp(28px, 3.5vw, 42px)", "line-height" to "1.22")
    props(".case-copy p", "max-width" to "58ch", "margin" to "0", "font-size" to "15px", "color" to "var(--fg-soft)")
    props(".case-tags", "display" to "flex", "flex-wrap" to "wrap", "gap" to "7px", "margin-top" to "28px")
    props(".case-tags span, .stack-cloud span", "padding" to "5px 8px", "border" to "1px solid var(--line)", "border-radius" to "4px",
        "font-family" to "var(--font-mono)", "font-size" to "10px", "color" to "var(--fg-mute)")
    props(".case-visual", "position" to "relative", "min-height" to "520px", "overflow" to "hidden", "background" to "#082b3c")
    props(".case-visual img", "position" to "absolute", "top" to "0", "left" to "0", "width" to "100%", "height" to "auto")

    props(".engineering-list", "border-top" to "1px solid var(--line)")
    props(".engineering-item", "display" to "grid", "grid-template-columns" to "56px minmax(0, 1fr) 220px", "gap" to "24px",
        "align-items" to "start", "width" to "100%", "padding" to "26px 0", "border" to "0", "border-bottom" to "1px solid var(--line)",
        "border-radius" to "0", "background" to "transparent", "color" to "var(--fg)", "font-family" to "var(--font-sans)",
        "text-align" to "left", "cursor" to "pointer", "appearance" to "none", "transition" to "background-color 160ms ease")
    props(".engineering-item:hover", "background" to "color-mix(in oklab, var(--fg) 2.5%, transparent)")
    props(".engineering-item:focus-visible", "outline" to "2px solid var(--accent)", "outline-offset" to "3px")
    props(".eng-index", "font-family" to "var(--font-mono)", "font-size" to "10px", "color" to "var(--accent)")
    props(".engineering-item h3", "margin" to "0 0 7px", "font-size" to "18px")
    props(".engineering-item p", "max-width" to "66ch", "margin" to "0", "color" to "var(--fg-soft)")
    props(".eng-meta", "padding-top" to "3px", "font-family" to "var(--font-mono)", "font-size" to "10px", "color" to "var(--fg-mute)")
    props(".eng-side", "display" to "grid", "grid-template-columns" to "minmax(0, 1fr) 24px", "align-items" to "center", "gap" to "14px", "min-height" to "24px")
    props(".eng-toggle", "display" to "inline-flex", "width" to "24px", "height" to "24px", "flex" to "0 0 24px", "align-items" to "center",
        "justify-content" to "center", "padding" to "0", "border" to "1px solid var(--line-2)", "border-radius" to "50%", "font-family" to "var(--font-sans)",
        "font-size" to "16px", "font-weight" to "400", "line-height" to "1", "transform-origin" to "center", "transition" to "transform 200ms ease, border-color 160ms ease")
    props(".engineering-item[data-open=\"true\"] .eng-toggle", "transform" to "rotate(180deg)", "border-color" to "var(--accent)")
    props(".engineering-item[data-open=\"true\"] h3", "color" to "var(--accent)")
    props(".eng-detail", "display" to "grid", "grid-template-rows" to "0fr", "grid-column" to "2 / 4", "margin-top" to "0", "padding-top" to "0",
        "border-top" to "1px solid transparent", "opacity" to "0", "transform" to "translateY(-4px)",
        "transition" to "grid-template-rows 220ms ease, opacity 180ms ease, transform 220ms ease, margin-top 220ms ease, padding-top 220ms ease, border-color 220ms ease")
    props(".engineering-item[data-open=\"true\"] .eng-detail", "grid-template-rows" to "1fr", "margin-top" to "2px", "padding-top" to "18px",
        "border-top-color" to "var(--line)", "opacity" to "1", "transform" to "translateY(0)")
    props(".eng-detail p", "min-height" to "0", "max-width" to "72ch", "overflow" to "hidden", "margin" to "0", "font-size" to "14px", "line-height" to "1.8", "color" to "var(--fg-soft)")
    style(".engineering-item, .eng-toggle, .eng-detail") { cssMedia("(prefers-reduced-motion: reduce)", "transition" to "none") }

    props(".work-grid", "display" to "grid", "grid-template-columns" to "repeat(3, 1fr)", "gap" to "12px")
    props(".work-card", "display" to "flex", "min-height" to "300px", "flex-direction" to "column", "padding" to "26px",
        "border" to "1px solid var(--line)", "border-radius" to "7px", "background" to "var(--bg-card)", "transition" to "border-color .18s, transform .18s")
    props(".work-card:first-child", "grid-column" to "auto", "min-height" to "300px", "background" to "var(--bg-card)")
    props(".work-card:hover", "border-color" to "var(--accent)", "transform" to "translateY(-2px)")
    props(".work-top, .work-bottom", "display" to "flex", "justify-content" to "space-between", "gap" to "20px",
        "font-family" to "var(--font-mono)", "font-size" to "10px", "color" to "var(--fg-mute)")
    props(".work-card h3", "margin" to "auto 0 14px", "font-size" to "clamp(28px, 4vw, 46px)")
    props(".work-card h3", "font-size" to "28px")
    props(".work-card p", "max-width" to "70ch", "margin" to "0 0 28px", "color" to "var(--fg-soft)")
    props(".work-bottom", "margin-top" to "auto")
    props(".work-bottom b", "color" to "var(--accent)")

    props(".experience-grid", "display" to "grid", "grid-template-columns" to "1.1fr .9fr", "gap" to "72px")
    props(".timeline", "border-top" to "1px solid var(--line)")
    props(".timeline-row", "display" to "grid", "grid-template-columns" to "130px 1fr", "gap" to "20px", "padding" to "19px 0", "border-bottom" to "1px solid var(--line)")
    props(".timeline-row > span", "font-family" to "var(--font-mono)", "font-size" to "10px", "color" to "var(--fg-mute)")
    props(".timeline-row > div", "display" to "flex", "flex-direction" to "column")
    props(".timeline-row b", "font-size" to "15px")
    props(".timeline-row div span", "font-size" to "12px", "color" to "var(--fg-mute)")
    props(".profile-notes", "padding" to "28px", "border-left" to "3px solid var(--accent)", "background" to "var(--bg-soft)")
    props(".profile-notes h3", "margin" to "6px 0 18px", "font-size" to "20px")
    props(".profile-notes p", "margin" to "0", "color" to "var(--fg-soft)")
    props(".oss-kicker", "font-family" to "var(--font-mono)", "font-size" to "9px", "color" to "var(--accent)", "letter-spacing" to ".08em")
    props(".oss-list", "border-top" to "1px solid var(--line)")
    props(".oss-entry", "display" to "grid", "grid-template-columns" to "minmax(0, 1fr) auto", "gap" to "14px", "align-items" to "center",
        "padding" to "14px 0", "border-bottom" to "1px solid var(--line)")
    props(".oss-entry > div", "display" to "flex", "flex-direction" to "column", "gap" to "2px", "min-width" to "0")
    props(".oss-entry b", "font-size" to "13px", "font-weight" to "650")
    props(".oss-entry div span", "font-size" to "11px", "color" to "var(--fg-mute)")
    props(".oss-pr", "font-family" to "var(--font-mono)", "font-size" to "9px", "color" to "var(--accent)", "white-space" to "nowrap")
    props(".profile-notes .background-note", "margin-top" to "18px", "padding-top" to "18px", "border-top" to "1px solid var(--line)",
        "font-size" to "12px", "color" to "var(--fg-mute)")
    props(".stack-cloud", "display" to "flex", "flex-wrap" to "wrap", "gap" to "7px", "margin-top" to "24px")

    props(".contact-new", "padding" to "100px 0")
    props(".contact-label", "font-family" to "var(--font-mono)", "font-size" to "10px", "color" to "var(--accent)")
    props(".contact-new h2", "max-width" to "780px", "margin" to "22px 0 18px", "font-size" to "clamp(36px, 6vw, 64px)",
        "line-height" to "1.16", "font-weight" to "680")
    props(".contact-new > p", "margin" to "0", "font-size" to "16px", "color" to "var(--fg-soft)")
    props(".contact-links", "display" to "flex", "flex-wrap" to "wrap", "gap" to "10px", "margin-top" to "38px")
    props(".contact-links a", "padding" to "10px 14px", "border" to "1px solid var(--line-2)", "border-radius" to "5px", "font-weight" to "600")
    props(".contact-links a:first-child", "background" to "var(--accent)", "border-color" to "var(--accent)", "color" to "#fff")

    style(".section-intro") { cssMedia("(max-width: 720px)", "grid-template-columns" to "1fr", "gap" to "10px", "margin-bottom" to "32px") }
    style(".section-intro > p") { cssMedia("(max-width: 720px)", "grid-column" to "1") }
    style(".impact-metrics") { cssMedia("(max-width: 720px)", "grid-template-columns" to "1fr") }
    style(".impact-metric") { cssMedia("(max-width: 720px)", "padding" to "22px 0", "border-right" to "0", "border-bottom" to "1px solid var(--line)") }
    style(".impact-metric + .impact-metric") { cssMedia("(max-width: 720px)", "padding-left" to "0") }
    style(".case-study") { cssMedia("(max-width: 720px)", "grid-template-columns" to "1fr", "min-height" to "0") }
    style(".case-copy") { cssMedia("(max-width: 720px)", "padding" to "28px 22px") }
    style(".case-visual") { cssMedia("(max-width: 720px)", "min-height" to "420px") }
    style(".engineering-item") { cssMedia("(max-width: 720px)", "grid-template-columns" to "32px 1fr", "gap" to "12px") }
    style(".eng-side") { cssMedia("(max-width: 720px)", "grid-column" to "2") }
    style(".eng-detail") { cssMedia("(max-width: 720px)", "grid-column" to "2") }
    style(".work-grid") { cssMedia("(max-width: 720px)", "grid-template-columns" to "1fr") }
    style(".work-card:first-child") { cssMedia("(max-width: 720px)", "grid-column" to "auto") }
    style(".work-card") { cssMedia("(max-width: 720px)", "min-height" to "280px", "padding" to "22px") }
    style(".experience-grid") { cssMedia("(max-width: 720px)", "grid-template-columns" to "1fr", "gap" to "36px") }
    style(".timeline-row") { cssMedia("(max-width: 720px)", "grid-template-columns" to "100px 1fr") }
}

// ---------- helpers ----------

private fun InitSilkContext.props(selector: String, vararg props: Pair<String, String>) {
    stylesheet.registerStyleBase(selector) {
        Modifier.styleModifier { props.forEach { (k, v) -> property(k, v) } }
    }
}

private fun InitSilkContext.style(selector: String, block: StyleScope.() -> Unit) {
    stylesheet.registerStyle(selector, block)
}

private fun StyleScope.css(vararg props: Pair<String, String>) {
    base { Modifier.styleModifier { props.forEach { (k, v) -> property(k, v) } } }
}

private fun StyleScope.cssAt(suffix: String, vararg props: Pair<String, String>) {
    cssRule(suffix) { Modifier.styleModifier { props.forEach { (k, v) -> property(k, v) } } }
}

private fun StyleScope.cssMedia(query: String, vararg props: Pair<String, String>) {
    cssRule(CSSMediaQuery.Raw(query)) {
        Modifier.styleModifier { props.forEach { (k, v) -> property(k, v) } }
    }
}

// ---------- tokens ----------

private fun InitSilkContext.tokens() {
    props(":root",
        "--bg" to "#0b0d0c",
        "--bg-soft" to "#111413",
        "--bg-card" to "#0f1211",
        "--line" to "#1d211f",
        "--line-2" to "#262b29",
        "--fg" to "#e6ebe7",
        "--fg-soft" to "#aab2ad",
        "--fg-mute" to "#6b746f",
        "--fg-faint" to "#3f4843",
        "--accent" to "#7aa97f",
        "--accent-2" to "#9ba88a",
        "--accent-dim" to "#2a3a2e",
        "--warn" to "#f0b429",
        "--rose" to "#ff6b81",
        "--grid" to "rgba(255,255,255,0.04)",
        "--font-sans" to "\"Pretendard Variable\", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, sans-serif",
        "--font-mono" to "\"JetBrains Mono\", ui-monospace, \"SF Mono\", Menlo, Consolas, monospace",
        "--max" to "880px",
        "--gutter" to "28px",
    )
    props(":root[data-theme=\"light\"]",
        "--bg" to "#f7f7f4",
        "--bg-soft" to "#efefe9",
        "--bg-card" to "#ffffff",
        "--line" to "#e3e3dc",
        "--line-2" to "#cdcdc3",
        "--fg" to "#131614",
        "--fg-soft" to "#44544b",
        "--fg-mute" to "#6b746f",
        "--fg-faint" to "#b1b8b3",
        "--accent" to "#4a7a4f",
        "--accent-2" to "#6b7a55",
        "--accent-dim" to "#c8d6c5",
        "--warn" to "#b97a06",
        "--rose" to "#c63f55",
        "--grid" to "rgba(0,0,0,0.05)",
    )
}

private fun InitSilkContext.tweakPalettes() {
    props(":root[data-palette=\"android\"]",
        "--accent" to "#3ddc84", "--accent-2" to "#a4c639")
    props(":root[data-palette=\"android\"][data-theme=\"light\"]",
        "--accent" to "#1f8b4c", "--accent-2" to "#5b8a1a")
    props(":root[data-palette=\"amber\"]",
        "--accent" to "#f0b429", "--accent-2" to "#d49419")
    props(":root[data-palette=\"amber\"][data-theme=\"light\"]",
        "--accent" to "#b97a06", "--accent-2" to "#8a5b00")
    props(":root[data-palette=\"mono\"]",
        "--accent" to "#cdd1cf", "--accent-2" to "#8a928d")
    props(":root[data-palette=\"mono\"][data-theme=\"light\"]",
        "--accent" to "#44544b", "--accent-2" to "#6b746f")
}

private fun InitSilkContext.densityVariants() {
    props(":root[data-density=\"compact\"]", "--gutter" to "22px")
    props(":root[data-density=\"roomy\"]", "--gutter" to "36px")
    props(":root[data-density=\"compact\"] section", "padding" to "40px 0")
    props(":root[data-density=\"roomy\"] section", "padding" to "96px 0")
    props(":root[data-density=\"compact\"] .hero",
        "padding-top" to "48px", "padding-bottom" to "16px")
    props(":root[data-density=\"roomy\"] .hero",
        "padding-top" to "120px", "padding-bottom" to "48px")
    props(":root[data-density=\"compact\"] .section-head", "margin-bottom" to "18px")
    props(":root[data-density=\"roomy\"] .section-head", "margin-bottom" to "40px")
    props(":root[data-density=\"compact\"] .projects", "gap" to "8px")
    props(":root[data-density=\"roomy\"] .projects", "gap" to "18px")
    props(":root[data-density=\"compact\"] .p-head", "padding" to "12px 14px")
    props(":root[data-density=\"roomy\"] .p-head", "padding" to "22px 22px")
}

private fun InitSilkContext.textureVariants() {
    props(":root[data-texture=\"none\"] body::before", "display" to "none")
    props(":root[data-texture=\"dots\"] body::before",
        "background-image" to "radial-gradient(var(--grid) 1px, transparent 1px)",
        "background-size" to "22px 22px",
        "opacity" to "0.7")
    props(":root[data-texture=\"scanlines\"] body::before",
        "background-image" to "repeating-linear-gradient(to bottom, var(--grid) 0 1px, transparent 1px 4px)",
        "background-size" to "auto",
        "opacity" to "0.45",
        "mask-image" to "linear-gradient(to bottom, black 0%, black 80%, transparent 100%)",
        "-webkit-mask-image" to "linear-gradient(to bottom, black 0%, black 80%, transparent 100%)")
}

// ---------- base ----------

private fun InitSilkContext.baseElements() {
    props("*", "box-sizing" to "border-box")
    props("html, body", "margin" to "0", "padding" to "0")
    props("html", "scroll-behavior" to "smooth")
    props("body",
        "background" to "var(--bg)",
        "color" to "var(--fg)",
        "font-family" to "var(--font-sans)",
        "font-size" to "15px",
        "line-height" to "1.55",
        "-webkit-font-smoothing" to "antialiased",
        "text-rendering" to "optimizeLegibility",
        "overflow-x" to "hidden")
    props("body::before",
        "content" to "\"\"",
        "position" to "fixed",
        "inset" to "0",
        "pointer-events" to "none",
        "background-image" to "linear-gradient(var(--grid) 1px, transparent 1px), linear-gradient(90deg, var(--grid) 1px, transparent 1px)",
        "background-size" to "56px 56px",
        "mask-image" to "radial-gradient(ellipse at 50% 20%, black 10%, transparent 60%)",
        "-webkit-mask-image" to "radial-gradient(ellipse at 50% 20%, black 10%, transparent 60%)",
        "opacity" to "0.55",
        "z-index" to "0")
    props("a", "color" to "inherit", "text-decoration" to "none")
    props("::selection", "background" to "var(--accent)", "color" to "#0b0d0c")
    props(".mono",
        "font-family" to "var(--font-mono)",
        "font-feature-settings" to "\"ss02\"")
}

// ---------- layout / topbar ----------

private fun InitSilkContext.layoutShellAndTopbar() {
    props(".shell",
        "position" to "relative",
        "z-index" to "1",
        "max-width" to "var(--max)",
        "margin" to "0 auto",
        "padding" to "0 var(--gutter)")

    props(".topbar",
        "position" to "sticky",
        "top" to "0",
        "z-index" to "50",
        "backdrop-filter" to "blur(10px)",
        "-webkit-backdrop-filter" to "blur(10px)",
        "background" to "color-mix(in oklab, var(--bg) 80%, transparent)",
        "border-bottom" to "1px solid var(--line)")

    props(".topbar-inner",
        "max-width" to "var(--max)",
        "margin" to "0 auto",
        "padding" to "12px var(--gutter)",
        "display" to "flex",
        "align-items" to "center",
        "gap" to "16px",
        "font-family" to "var(--font-mono)",
        "font-size" to "12.5px")

    props(".brand",
        "display" to "flex",
        "align-items" to "center",
        "gap" to "8px",
        "color" to "var(--fg)",
        "letter-spacing" to "0.02em")
    props(".brand .dot",
        "width" to "7px",
        "height" to "7px",
        "border-radius" to "50%",
        "background" to "var(--accent)",
        "opacity" to "0.9")
    props(".brand b", "font-weight" to "600")
    props(".brand span", "color" to "var(--fg-mute)")

    props(".nav", "margin-left" to "auto", "display" to "flex", "gap" to "4px")
    style(".nav a") {
        css(
            "padding" to "6px 10px",
            "color" to "var(--fg-soft)",
            "border-radius" to "4px",
            "transition" to "background .15s, color .15s")
        cssAt(":hover", "color" to "var(--fg)", "background" to "var(--bg-soft)")
    }
    style(".nav a .hash") {
        css("color" to "var(--fg-faint)")
    }
    style(".nav a:hover .hash") {
        css("color" to "var(--fg-soft)")
    }

    style(".theme-toggle") {
        css(
            "width" to "30px",
            "height" to "30px",
            "display" to "inline-flex",
            "align-items" to "center",
            "justify-content" to "center",
            "border" to "1px solid var(--line)",
            "border-radius" to "4px",
            "background" to "transparent",
            "color" to "var(--fg-soft)",
            "cursor" to "pointer",
            "transition" to "border-color .15s, color .15s")
        cssAt(":hover", "color" to "var(--fg)", "border-color" to "var(--line-2)")
    }
    props(".theme-toggle svg", "width" to "14px", "height" to "14px")
}

private fun InitSilkContext.crumb() {
    props(".crumb", "color" to "var(--fg-mute)", "margin-left" to "6px")
    props(".crumb .sep", "color" to "var(--fg-faint)", "margin" to "0 6px")
    style(".crumb a") {
        css("color" to "var(--fg-soft)")
        cssAt(":hover", "color" to "var(--fg)")
    }
    props(".crumb b", "color" to "var(--fg)", "font-weight" to "500")
}

// ---------- section primitives ----------

private fun InitSilkContext.sectionPrimitives() {
    props("section", "padding" to "64px 0", "position" to "relative")
    props(".section-head",
        "display" to "flex",
        "align-items" to "baseline",
        "gap" to "14px",
        "margin-bottom" to "28px")
    props(".section-head .num",
        "font-family" to "var(--font-mono)",
        "font-size" to "12px",
        "color" to "var(--fg-faint)",
        "letter-spacing" to "0.05em")
    style(".section-head h2") {
        css(
            "margin" to "0",
            "font-family" to "var(--font-mono)",
            "font-size" to "13px",
            "font-weight" to "500",
            "letter-spacing" to "0.18em",
            "text-transform" to "uppercase",
            "color" to "var(--fg)")
        cssAt("::before", "content" to "\"// \"", "color" to "var(--fg-faint)")
    }
    props(".section-head .rule",
        "flex" to "1",
        "height" to "1px",
        "background" to "linear-gradient(to right, var(--line), transparent)")
}

// ---------- hero ----------

private fun InitSilkContext.hero() {
    props(".hero",
        "padding-top" to "80px",
        "padding-bottom" to "24px",
        "position" to "relative")
    props(".hero-meta",
        "font-family" to "var(--font-mono)",
        "font-size" to "12px",
        "color" to "var(--fg-mute)",
        "display" to "flex",
        "gap" to "14px",
        "margin-bottom" to "22px",
        "flex-wrap" to "wrap")
    props(".hero-meta .ok", "color" to "var(--accent)")
    props(".hero-meta .pill",
        "display" to "inline-flex",
        "align-items" to "center",
        "gap" to "6px")
    props(".hero-meta .pill .led",
        "width" to "6px",
        "height" to "6px",
        "border-radius" to "50%",
        "background" to "var(--accent)")

    props(".hero h1",
        "margin" to "0 0 6px",
        "font-size" to "clamp(40px, 7.5vw, 76px)",
        "line-height" to "0.98",
        "letter-spacing" to "-0.035em",
        "font-weight" to "700",
        "color" to "var(--fg)")
    props(".hero h1 .grn", "color" to "var(--accent)")
    props(".hero h1 .caret",
        "display" to "inline-block",
        "width" to "0.5ch",
        "height" to "0.9em",
        "background" to "var(--fg)",
        "margin-left" to "4px",
        "vertical-align" to "-0.06em",
        "animation" to "blink 1.05s steps(1) infinite",
        "opacity" to "0.65")

    stylesheet.registerKeyframes("blink") {
        50.percent { Modifier.opacity(0) }
    }

    props(".hero .role",
        "font-family" to "var(--font-mono)",
        "font-size" to "14px",
        "color" to "var(--fg-soft)",
        "margin" to "14px 0 22px")
    props(".hero .role .arrow", "color" to "var(--accent)")
    props(".hero .bio",
        "max-width" to "56ch",
        "color" to "var(--fg-soft)",
        "font-size" to "16px",
        "line-height" to "1.7")
    props(".hero .bio b", "color" to "var(--fg)", "font-weight" to "600")

    props(".hero-stats",
        "margin-top" to "36px",
        "display" to "grid",
        "grid-template-columns" to "repeat(4, 1fr)",
        "gap" to "0",
        "border" to "1px solid var(--line)",
        "border-radius" to "6px",
        "overflow" to "hidden",
        "background" to "var(--bg-card)")
    style(".hero-stats .stat") {
        css("padding" to "16px 18px", "border-right" to "1px solid var(--line)")
        cssAt(":last-child", "border-right" to "none")
    }
    props(".hero-stats .k",
        "font-family" to "var(--font-mono)",
        "font-size" to "10.5px",
        "text-transform" to "uppercase",
        "letter-spacing" to "0.12em",
        "color" to "var(--fg-mute)")
    props(".hero-stats .v",
        "font-family" to "var(--font-mono)",
        "font-size" to "22px",
        "color" to "var(--fg)",
        "font-weight" to "600",
        "margin-top" to "6px",
        "letter-spacing" to "-0.01em")
    props(".hero-stats .v small",
        "font-size" to "11px",
        "color" to "var(--fg-mute)",
        "margin-left" to "4px",
        "font-weight" to "400")

    style(".hero-stats") {
        cssMedia("(max-width: 640px)", "grid-template-columns" to "repeat(2, 1fr)")
    }
    style(".hero-stats .stat:nth-child(2)") {
        cssMedia("(max-width: 640px)", "border-right" to "none")
    }
    style(".hero-stats .stat:nth-child(1), .hero-stats .stat:nth-child(2)") {
        cssMedia("(max-width: 640px)", "border-bottom" to "1px solid var(--line)")
    }
}

// ---------- skills ----------

private fun InitSilkContext.skills() {
    style(".skills-grid") {
        css(
            "display" to "grid",
            "grid-template-columns" to "repeat(3, 1fr)",
            "gap" to "12px")
        cssMedia("(max-width: 720px)", "grid-template-columns" to "1fr")
    }
    props(".skill-group",
        "border" to "1px solid var(--line)",
        "border-radius" to "6px",
        "background" to "var(--bg-card)",
        "padding" to "16px")
    style(".skill-group h3") {
        css(
            "margin" to "0 0 14px",
            "font-family" to "var(--font-mono)",
            "font-size" to "11px",
            "font-weight" to "500",
            "letter-spacing" to "0.14em",
            "text-transform" to "uppercase",
            "color" to "var(--fg-mute)",
            "display" to "flex",
            "align-items" to "center",
            "gap" to "6px")
        cssAt("::before", "content" to "\"▸\"", "color" to "var(--fg-faint)", "font-size" to "10px")
    }
    props(".skill-list",
        "list-style" to "none",
        "padding" to "0",
        "margin" to "0",
        "display" to "flex",
        "flex-direction" to "column",
        "gap" to "4px")
    style(".skill-list li") {
        css(
            "display" to "grid",
            "grid-template-columns" to "1fr auto",
            "align-items" to "center",
            "gap" to "10px",
            "padding" to "6px 8px",
            "border-radius" to "4px",
            "font-family" to "var(--font-mono)",
            "font-size" to "13px",
            "color" to "var(--fg)",
            "transition" to "background .15s",
            "cursor" to "default")
        cssAt(":hover", "background" to "var(--bg-soft)")
    }
    props(".skill-list li .meta",
        "font-size" to "10.5px",
        "color" to "var(--fg-faint)",
        "letter-spacing" to "0.04em")
}

// ---------- projects ----------

private fun InitSilkContext.projects() {
    props(".projects",
        "display" to "flex",
        "flex-direction" to "column",
        "gap" to "10px")
    style(".project") {
        css(
            "border" to "1px solid var(--line)",
            "border-radius" to "8px",
            "background" to "var(--bg-card)",
            "overflow" to "hidden",
            "transition" to "border-color .18s, background .18s, transform .18s")
        cssAt(":hover", "border-color" to "var(--line-2)")
    }
    props(".p-head",
        "display" to "grid",
        "grid-template-columns" to "auto 1fr auto auto",
        "align-items" to "center",
        "gap" to "14px",
        "padding" to "16px 18px",
        "cursor" to "pointer",
        "user-select" to "none")
    props(".p-idx",
        "font-family" to "var(--font-mono)",
        "font-size" to "11px",
        "color" to "var(--fg-faint)",
        "letter-spacing" to "0.05em")
    props(".p-title-wrap",
        "display" to "flex",
        "flex-direction" to "column",
        "gap" to "2px",
        "min-width" to "0")
    props(".p-title",
        "font-size" to "18px",
        "font-weight" to "600",
        "color" to "var(--fg)",
        "letter-spacing" to "-0.01em",
        "display" to "flex",
        "align-items" to "center",
        "gap" to "10px")
    props(".p-role",
        "font-family" to "var(--font-mono)",
        "font-size" to "11.5px",
        "color" to "var(--fg-mute)")
    props(".p-role .at", "color" to "var(--fg-faint)")
    props(".p-status",
        "font-family" to "var(--font-mono)",
        "font-size" to "10.5px",
        "padding" to "3px 8px",
        "border-radius" to "999px",
        "border" to "1px solid var(--line-2)",
        "color" to "var(--fg-soft)",
        "letter-spacing" to "0.06em",
        "white-space" to "nowrap")
    props(".p-status.live",
        "color" to "var(--accent)",
        "border-color" to "color-mix(in oklab, var(--accent) 35%, var(--line))")
    props(".p-status.archive", "color" to "var(--fg-mute)")
    props(".p-status.maint",
        "color" to "var(--warn)",
        "border-color" to "color-mix(in oklab, var(--warn) 50%, var(--line))")

    props(".p-toggle",
        "width" to "24px",
        "height" to "24px",
        "display" to "inline-flex",
        "align-items" to "center",
        "justify-content" to "center",
        "color" to "var(--fg-mute)",
        "transition" to "transform .25s ease, color .15s")
    props(".project[data-open=\"true\"] .p-toggle",
        "transform" to "rotate(90deg)",
        "color" to "var(--fg-soft)")

    props(".p-body",
        "display" to "grid",
        "grid-template-rows" to "0fr",
        "transition" to "grid-template-rows .35s ease")
    props(".project[data-open=\"true\"] .p-body", "grid-template-rows" to "1fr")
    props(".p-body > div", "overflow" to "hidden", "min-height" to "0")
    props(".p-body-inner",
        "padding" to "0 18px 20px 18px",
        "border-top" to "1px dashed var(--line)",
        "margin-top" to "0",
        "padding-top" to "18px")
    props(".p-desc",
        "color" to "var(--fg-soft)",
        "font-size" to "14.5px",
        "line-height" to "1.75",
        "max-width" to "64ch",
        "margin" to "0 0 18px")

    props(".p-modules",
        "display" to "grid",
        "grid-template-columns" to "repeat(auto-fill, minmax(220px, 1fr))",
        "gap" to "10px")
    props(".p-mod",
        "border" to "1px solid var(--line)",
        "border-radius" to "6px",
        "padding" to "12px 14px",
        "background" to "var(--bg-soft)")
    props(".p-mod-name",
        "font-family" to "var(--font-mono)",
        "font-size" to "11px",
        "text-transform" to "uppercase",
        "letter-spacing" to "0.1em",
        "color" to "var(--fg-mute)",
        "margin-bottom" to "8px")
    props(".p-mod-tags",
        "display" to "flex",
        "flex-wrap" to "wrap",
        "gap" to "6px",
        "margin-bottom" to "10px")
    props(".p-tag",
        "font-family" to "var(--font-mono)",
        "font-size" to "10.5px",
        "padding" to "3px 7px",
        "border-radius" to "3px",
        "background" to "var(--bg-card)",
        "color" to "var(--fg-soft)",
        "border" to "1px solid var(--line)")
    props(".p-mod-links", "display" to "flex", "gap" to "8px", "flex-wrap" to "wrap")
    style(".p-link") {
        css(
            "font-family" to "var(--font-mono)",
            "font-size" to "11px",
            "color" to "var(--fg-soft)",
            "border-bottom" to "1px dashed var(--line-2)",
            "padding-bottom" to "1px",
            "transition" to "color .15s, border-color .15s")
        cssAt(":hover", "color" to "var(--fg)", "border-color" to "var(--fg-soft)")
        cssAt("::before", "content" to "\"↗ \"", "color" to "var(--fg-faint)")
    }
}

// ---------- contributions ----------

private fun InitSilkContext.contributions() {
    props(".contrib-card",
        "border" to "1px solid var(--line)",
        "border-radius" to "8px",
        "background" to "var(--bg-card)",
        "padding" to "20px")
    props(".contrib-head",
        "display" to "flex",
        "align-items" to "baseline",
        "justify-content" to "space-between",
        "margin-bottom" to "16px",
        "flex-wrap" to "wrap",
        "gap" to "10px")
    props(".contrib-head .total",
        "font-family" to "var(--font-mono)",
        "font-size" to "13px",
        "color" to "var(--fg)")
    props(".contrib-head .total b", "color" to "var(--fg)", "font-weight" to "600")
    props(".contrib-head .range",
        "font-family" to "var(--font-mono)",
        "font-size" to "11px",
        "color" to "var(--fg-mute)")

    props(".contrib-graph",
        "display" to "grid",
        "grid-template-columns" to "repeat(53, 1fr)",
        "gap" to "3px",
        "overflow-x" to "auto",
        "padding" to "4px")
    props(".contrib-week",
        "display" to "grid",
        "grid-template-rows" to "repeat(7, 12px)",
        "grid-auto-rows" to "0",
        "grid-auto-flow" to "row",
        "gap" to "3px")
    style(".contrib-day") {
        css(
            "border-radius" to "2px",
            "background" to "var(--bg-soft)",
            "transition" to "transform .12s")
        cssAt(":hover", "transform" to "scale(1.5)", "z-index" to "2")
    }
    props(".contrib-day[data-l=\"0\"]", "background" to "var(--bg-soft)")
    props(".contrib-day[data-l=\"1\"]",
        "background" to "color-mix(in oklab, var(--accent) 25%, var(--bg-soft))")
    props(".contrib-day[data-l=\"2\"]",
        "background" to "color-mix(in oklab, var(--accent) 50%, var(--bg-soft))")
    props(".contrib-day[data-l=\"3\"]",
        "background" to "color-mix(in oklab, var(--accent) 75%, var(--bg-soft))")
    props(".contrib-day[data-l=\"4\"]", "background" to "var(--accent)")

    props(".contrib-legend",
        "margin-top" to "14px",
        "display" to "flex",
        "align-items" to "center",
        "gap" to "6px",
        "font-family" to "var(--font-mono)",
        "font-size" to "11px",
        "color" to "var(--fg-mute)",
        "justify-content" to "flex-end")
    props(".contrib-legend .sq",
        "width" to "10px", "height" to "10px", "border-radius" to "2px")

    props(".contrib-mini",
        "display" to "grid",
        "grid-template-columns" to "repeat(3, 1fr)",
        "gap" to "10px",
        "margin-top" to "16px")
    props(".contrib-mini .m",
        "border" to "1px solid var(--line)",
        "border-radius" to "6px",
        "padding" to "12px",
        "background" to "var(--bg-soft)")
    props(".contrib-mini .k",
        "font-family" to "var(--font-mono)",
        "font-size" to "10.5px",
        "color" to "var(--fg-mute)",
        "text-transform" to "uppercase",
        "letter-spacing" to "0.1em")
    props(".contrib-mini .v",
        "font-family" to "var(--font-mono)",
        "font-size" to "18px",
        "color" to "var(--fg)",
        "font-weight" to "600",
        "margin-top" to "4px")
}

// ---------- contact ----------

private fun InitSilkContext.contact() {
    props(".contact-block",
        "border" to "1px solid var(--line)",
        "border-radius" to "8px",
        "background" to "var(--bg-card)",
        "overflow" to "hidden")
    props(".contact-tabbar",
        "display" to "flex",
        "background" to "var(--bg-soft)",
        "border-bottom" to "1px solid var(--line)",
        "padding" to "8px 14px",
        "gap" to "14px",
        "align-items" to "center",
        "font-family" to "var(--font-mono)",
        "font-size" to "11.5px",
        "color" to "var(--fg-mute)")
    props(".contact-tabbar .dots", "display" to "flex", "gap" to "6px")
    props(".contact-tabbar .dots .mac-dot",
        "width" to "10px",
        "height" to "10px",
        "border-radius" to "50%",
        "background" to "var(--line-2)",
        "display" to "inline-block")
    props(".contact-tabbar .dots .mac-dot:nth-child(1)", "background" to "#ff5f57")
    props(".contact-tabbar .dots .mac-dot:nth-child(2)", "background" to "#febc2e")
    props(".contact-tabbar .dots .mac-dot:nth-child(3)", "background" to "#28c840")

    props(".contact-body",
        "padding" to "22px 24px",
        "font-family" to "var(--font-mono)",
        "font-size" to "14px",
        "line-height" to "1.9",
        "color" to "var(--fg-soft)")
    props(".contact-body .pl", "color" to "var(--fg-faint)", "user-select" to "none")
    props(".contact-body .cmd", "color" to "var(--fg)")
    props(".contact-body .out",
        "color" to "var(--fg-soft)",
        "padding-left" to "16px",
        "display" to "block")
    style(".contact-body a") {
        css("color" to "var(--fg)", "border-bottom" to "1px dashed var(--line-2)")
        cssAt(":hover", "color" to "var(--fg)", "border-color" to "var(--fg-soft)")
    }
    props(".contact-body .key", "color" to "var(--fg-mute)")
}

// ---------- footer ----------

private fun InitSilkContext.footer() {
    props("footer",
        "border-top" to "1px solid var(--line)",
        "margin-top" to "40px",
        "padding" to "24px 0 40px",
        "font-family" to "var(--font-mono)",
        "font-size" to "11.5px",
        "color" to "var(--fg-mute)",
        "display" to "flex",
        "justify-content" to "space-between",
        "flex-wrap" to "wrap",
        "gap" to "8px")
    props("footer .build", "color" to "var(--fg-faint)")
}

// ---------- reveal animation ----------

private fun InitSilkContext.revealAnimation() {
    style(".reveal") {
        css(
            "opacity" to "0",
            "transform" to "translateY(8px)",
            "transition" to "opacity .6s ease, transform .6s ease")
    }
    props(".reveal.in", "opacity" to "1", "transform" to "none")

    style(".reveal") {
        cssMedia("(prefers-reduced-motion: reduce)",
            "opacity" to "1", "transform" to "none", "transition" to "none")
    }
    style(".hero h1 .caret") {
        cssMedia("(prefers-reduced-motion: reduce)", "animation" to "none")
    }
    style(".brand .dot") {
        cssMedia("(prefers-reduced-motion: reduce)", "animation" to "none")
    }
}

// ---------- responsive: tablet (<=900) ----------

private fun InitSilkContext.responsiveTablet() {
    val q = "(max-width: 900px)"
    style(":root") { cssMedia(q, "--gutter" to "22px") }
    style(".nav") { cssMedia(q, "display" to "none") }
    style(".topbar-inner") { cssMedia(q, "gap" to "12px") }
    style(".theme-toggle") { cssMedia(q, "margin-left" to "auto") }
    style("section") { cssMedia(q, "padding" to "48px 0") }
    style(".hero") { cssMedia(q, "padding-top" to "56px", "padding-bottom" to "16px") }
    style(".section-head") { cssMedia(q, "margin-bottom" to "22px", "gap" to "10px") }
    style(".hero-stats") { cssMedia(q, "grid-template-columns" to "repeat(2, 1fr)") }
    style(".hero-stats .stat:nth-child(2)") { cssMedia(q, "border-right" to "none") }
    style(".hero-stats .stat:nth-child(1), .hero-stats .stat:nth-child(2)") {
        cssMedia(q, "border-bottom" to "1px solid var(--line)")
    }
    style(".p-head") { cssMedia(q, "gap" to "10px", "padding" to "14px 14px") }
    style(".p-body-inner") { cssMedia(q, "padding" to "16px 14px 18px") }
    style(".p-modules") { cssMedia(q, "grid-template-columns" to "1fr") }
    style(".p-title") { cssMedia(q, "font-size" to "16.5px") }
}

// ---------- responsive: mobile (<=640) ----------

private fun InitSilkContext.responsiveMobile() {
    val q = "(max-width: 640px)"
    style(":root") { cssMedia(q, "--gutter" to "18px") }
    style(".topbar-inner") { cssMedia(q, "padding" to "10px var(--gutter)", "font-size" to "12px") }
    style(".brand") { cssMedia(q, "font-size" to "12.5px") }
    style("section") { cssMedia(q, "padding" to "40px 0") }
    style(".hero") { cssMedia(q, "padding-top" to "40px") }
    style(".hero-meta") {
        cssMedia(q, "gap" to "10px 14px", "font-size" to "11px", "margin-bottom" to "18px")
    }
    style(".hero h1") {
        cssMedia(q, "font-size" to "clamp(34px, 11vw, 56px)", "letter-spacing" to "-0.03em")
    }
    style(".hero .role") { cssMedia(q, "font-size" to "13px", "margin" to "12px 0 18px") }
    style(".hero .bio") { cssMedia(q, "font-size" to "15px", "line-height" to "1.65") }
    style(".hero-stats") { cssMedia(q, "grid-template-columns" to "1fr", "margin-top" to "28px") }
    style(".hero-stats .stat") {
        cssMedia(q, "border-right" to "none", "border-bottom" to "1px solid var(--line)")
    }
    style(".hero-stats .stat:last-child") { cssMedia(q, "border-bottom" to "none") }
    style(".hero-stats .v") { cssMedia(q, "font-size" to "19px") }
    style(".section-head") { cssMedia(q, "gap" to "10px", "flex-wrap" to "wrap") }
    style(".section-head .rule") { cssMedia(q, "min-width" to "40px") }
    style(".p-head") {
        cssMedia(q,
            "grid-template-columns" to "1fr auto",
            "align-items" to "start",
            "gap" to "8px",
            "padding" to "14px 14px")
    }
    style(".p-idx") { cssMedia(q, "display" to "none") }
    style(".p-toggle") { cssMedia(q, "align-self" to "center") }
    style(".p-title") { cssMedia(q, "font-size" to "16px", "gap" to "8px", "flex-wrap" to "wrap") }
    style(".p-role") { cssMedia(q, "font-size" to "11px") }
    style(".p-desc") { cssMedia(q, "font-size" to "14px") }
    style(".contrib-card") { cssMedia(q, "padding" to "16px 14px") }
    style(".contrib-head") { cssMedia(q, "gap" to "6px") }
    style(".contrib-graph") {
        cssMedia(q, "grid-template-columns" to "repeat(53, 10px)", "gap" to "2px")
    }
    style(".contrib-week") {
        cssMedia(q, "grid-template-rows" to "repeat(7, 10px)", "gap" to "2px")
    }
    style(".contrib-mini") { cssMedia(q, "grid-template-columns" to "repeat(2, 1fr)") }
    style(".contact-body") {
        cssMedia(q, "padding" to "18px 16px", "font-size" to "12.5px", "line-height" to "1.85")
    }
    style(".contact-body .out") { cssMedia(q, "padding-left" to "12px") }
    style(".contact-tabbar") { cssMedia(q, "padding" to "7px 12px") }
    style("footer") {
        cssMedia(q, "padding" to "20px 0 32px", "flex-direction" to "column", "align-items" to "flex-start")
    }
}

// ---------- responsive: small mobile (<=420) ----------

private fun InitSilkContext.responsiveSmallMobile() {
    val q = "(max-width: 420px)"
    style(":root") { cssMedia(q, "--gutter" to "16px") }
    style(".hero h1") { cssMedia(q, "font-size" to "36px") }
    style(".hero-meta") { cssMedia(q, "font-size" to "10.5px") }
    style(".section-head h2") { cssMedia(q, "font-size" to "12px", "letter-spacing" to "0.14em") }
    style(".p-title") { cssMedia(q, "font-size" to "15px") }
    style(".contact-body") { cssMedia(q, "font-size" to "11.5px") }
    style(".contrib-graph") { cssMedia(q, "grid-template-columns" to "repeat(53, 8px)") }
    style(".contrib-week") { cssMedia(q, "grid-template-rows" to "repeat(7, 8px)") }
}

// ---------- tweaks panel ----------

private fun InitSilkContext.tweaksPanel() {
    style(".twk-root") {
        css(
            "position" to "fixed",
            "right" to "16px",
            "bottom" to "16px",
            "z-index" to "100",
            "font-family" to "var(--font-mono)")
        cssMedia("(max-width: 480px)", "right" to "12px", "bottom" to "12px")
    }
    style(".twk-fab") {
        css(
            "width" to "36px",
            "height" to "36px",
            "display" to "inline-flex",
            "align-items" to "center",
            "justify-content" to "center",
            "border" to "1px solid var(--line)",
            "border-radius" to "999px",
            "background" to "color-mix(in oklab, var(--bg-card) 90%, transparent)",
            "backdrop-filter" to "blur(10px)",
            "-webkit-backdrop-filter" to "blur(10px)",
            "color" to "var(--fg-soft)",
            "cursor" to "pointer",
            "transition" to "border-color .15s, color .15s, background .15s",
            "font-size" to "14px",
            "line-height" to "1",
            "padding" to "0")
        cssAt(":hover", "color" to "var(--fg)", "border-color" to "var(--line-2)")
    }
    props(".twk-root[data-open=\"true\"] .twk-fab",
        "color" to "var(--fg)",
        "border-color" to "color-mix(in oklab, var(--accent) 40%, var(--line-2))")

    style(".twk-panel") {
        css(
            "position" to "absolute",
            "right" to "0",
            "bottom" to "44px",
            "width" to "240px",
            "border" to "1px solid var(--line)",
            "border-radius" to "8px",
            "background" to "color-mix(in oklab, var(--bg-card) 92%, transparent)",
            "backdrop-filter" to "blur(14px)",
            "-webkit-backdrop-filter" to "blur(14px)",
            "box-shadow" to "0 10px 30px rgba(0,0,0,.18)",
            "overflow" to "hidden",
            "display" to "flex",
            "flex-direction" to "column")
        cssMedia("(max-width: 480px)", "width" to "220px")
    }
    props(".twk-hd",
        "display" to "flex",
        "align-items" to "center",
        "justify-content" to "space-between",
        "padding" to "8px 10px 8px 14px",
        "border-bottom" to "1px solid var(--line)",
        "background" to "var(--bg-soft)")
    props(".twk-title",
        "font-size" to "11.5px",
        "letter-spacing" to "0.1em",
        "text-transform" to "uppercase",
        "color" to "var(--fg)")
    style(".twk-x") {
        css(
            "appearance" to "none",
            "border" to "none",
            "background" to "transparent",
            "color" to "var(--fg-mute)",
            "width" to "22px",
            "height" to "22px",
            "border-radius" to "4px",
            "cursor" to "pointer",
            "font-size" to "14px",
            "line-height" to "1",
            "padding" to "0")
        cssAt(":hover", "color" to "var(--fg)", "background" to "var(--bg-card)")
    }
    props(".twk-body",
        "padding" to "12px 14px 14px",
        "display" to "flex",
        "flex-direction" to "column",
        "gap" to "6px")
    style(".twk-sect") {
        css(
            "font-size" to "10px",
            "text-transform" to "uppercase",
            "letter-spacing" to "0.14em",
            "color" to "var(--fg-mute)",
            "margin-top" to "6px")
        cssAt(":first-child", "margin-top" to "0")
    }
    props(".twk-seg",
        "display" to "flex",
        "flex-wrap" to "wrap",
        "gap" to "4px",
        "padding" to "0")
    style(".twk-opt") {
        css(
            "appearance" to "none",
            "flex" to "1 1 0",
            "min-width" to "0",
            "border" to "1px solid var(--line)",
            "background" to "var(--bg-card)",
            "color" to "var(--fg-soft)",
            "font" to "inherit",
            "font-size" to "11px",
            "padding" to "5px 8px",
            "border-radius" to "4px",
            "cursor" to "pointer",
            "transition" to "border-color .12s, color .12s, background .12s",
            "text-align" to "center")
        cssAt(":hover", "color" to "var(--fg)", "border-color" to "var(--line-2)")
    }
    props(".twk-opt[data-active=\"true\"]",
        "color" to "var(--fg)",
        "border-color" to "color-mix(in oklab, var(--accent) 40%, var(--line))",
        "background" to "color-mix(in oklab, var(--accent) 10%, var(--bg-card))")
}
