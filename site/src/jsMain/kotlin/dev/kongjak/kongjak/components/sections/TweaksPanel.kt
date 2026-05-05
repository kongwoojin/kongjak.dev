package dev.kongjak.kongjak.components.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.browser.document
import kotlinx.browser.localStorage
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

private const val TWEAKS_KEY = "kongjak:tweaks"

private data class Tweaks(
    val palette: String = "android",
    val density: String = "regular",
    val texture: String = "none",
)

private fun loadTweaks(): Tweaks {
    val raw = localStorage.getItem(TWEAKS_KEY) ?: return Tweaks()
    val palette = parseField(raw, "palette") ?: "android"
    val density = parseField(raw, "density") ?: "regular"
    val texture = parseField(raw, "texture") ?: "none"
    return Tweaks(palette, density, texture)
}

private fun parseField(json: String, key: String): String? {
    val needle = "\"$key\""
    val keyIndex = json.indexOf(needle)
    if (keyIndex < 0) return null
    val colonIndex = json.indexOf(':', keyIndex + needle.length)
    if (colonIndex < 0) return null
    val openQuote = json.indexOf('"', colonIndex + 1)
    if (openQuote < 0) return null
    val closeQuote = json.indexOf('"', openQuote + 1)
    if (closeQuote < 0) return null
    return json.substring(openQuote + 1, closeQuote)
}

private fun saveTweaks(t: Tweaks) {
    val json = "{\"palette\":\"${t.palette}\",\"density\":\"${t.density}\",\"texture\":\"${t.texture}\"}"
    localStorage.setItem(TWEAKS_KEY, json)
}

private fun applyTweaks(t: Tweaks) {
    val root = document.documentElement ?: return
    if (t.palette == "sage") root.removeAttribute("data-palette") else root.setAttribute("data-palette", t.palette)
    if (t.density == "regular") root.removeAttribute("data-density") else root.setAttribute("data-density", t.density)
    if (t.texture == "grid") root.removeAttribute("data-texture") else root.setAttribute("data-texture", t.texture)
}

private val PALETTE_OPTIONS = listOf(
    "sage" to "Sage",
    "android" to "Android",
    "amber" to "Amber",
    "mono" to "Mono",
)

private val DENSITY_OPTIONS = listOf(
    "compact" to "Compact",
    "regular" to "Regular",
    "roomy" to "Roomy",
)

private val TEXTURE_OPTIONS = listOf(
    "grid" to "Grid",
    "dots" to "Dots",
    "scanlines" to "CRT",
    "none" to "None",
)

@Composable
fun TweaksPanel(showDensity: Boolean = true) {
    val initial = remember { loadTweaks() }
    var tweaks by remember { mutableStateOf(initial) }
    var open by remember { mutableStateOf(false) }

    // Skip the inline-head-script's already-applied state on first composition;
    // only re-apply + persist after the user actually changes something.
    LaunchedEffect(tweaks) {
        if (tweaks != initial) {
            applyTweaks(tweaks)
            saveTweaks(tweaks)
        }
    }

    Div(attrs = { classes("twk-root"); attr("data-open", if (open) "true" else "false") }) {
        Button(attrs = {
            classes("twk-fab")
            attr("aria-label", "Tweaks")
            attr("aria-expanded", if (open) "true" else "false")
            onClick { open = !open }
        }) {
            Span(attrs = { classes("mono") }) { Text(if (open) "×" else "✦") }
        }

        if (open) {
            Div(attrs = { classes("twk-panel") }) {
                Div(attrs = { classes("twk-hd") }) {
                    Span(attrs = { classes("twk-title", "mono") }) { Text("Tweaks") }
                    Button(attrs = {
                        classes("twk-x")
                        attr("aria-label", "닫기")
                        onClick { open = false }
                    }) { Text("×") }
                }

                Div(attrs = { classes("twk-body") }) {
                    TweakSection("Palette", tweaks.palette, PALETTE_OPTIONS) {
                        tweaks = tweaks.copy(palette = it)
                    }
                    if (showDensity) {
                        TweakSection("Density", tweaks.density, DENSITY_OPTIONS) {
                            tweaks = tweaks.copy(density = it)
                        }
                    }
                    TweakSection("Texture", tweaks.texture, TEXTURE_OPTIONS) {
                        tweaks = tweaks.copy(texture = it)
                    }
                }
            }
        }
    }
}

@Composable
private fun TweakSection(
    label: String,
    value: String,
    options: List<Pair<String, String>>,
    onChange: (String) -> Unit,
) {
    Div(attrs = { classes("twk-sect") }) { Text(label) }
    Div(attrs = { classes("twk-seg") }) {
        for ((v, optionLabel) in options) {
            Button(attrs = {
                classes("twk-opt")
                attr("data-active", if (v == value) "true" else "false")
                onClick { onChange(v) }
            }) { Text(optionLabel) }
        }
    }
}
