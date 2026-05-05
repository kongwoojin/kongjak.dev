package dev.kongjak.kongjak

import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.palette.background
import com.varabyte.kobweb.silk.theme.colors.palette.color

class SitePalette(
    val nearBackground: Color,
    val cardBackground: Color,
    val title: Color,
    val description: Color,
    val divider: Color,
    val accent: Color,
    val link: Color,
    val codeBlock: Color
)

object SitePalettes {
    val light = SitePalette(
        nearBackground = Color.rgb(250, 250, 250),
        cardBackground = Color.rgba(0, 0, 0, 0.03F),
        title = Color.rgb(20, 20, 20),
        description = Color.rgba(0, 0, 0, 0.6F),
        divider = Color.rgba(0, 0, 0, 0.08F),
        accent = Color.rgb(0x008CA0),
        link = Color.rgba(0, 0, 0, 0.5F),
        codeBlock = Color.rgb(244, 244, 246)
    )
    val dark = SitePalette(
        nearBackground = Color.rgb(12, 12, 12),
        cardBackground = Color.rgba(255, 255, 255, 0.03F),
        title = Colors.White,
        description = Color.rgba(255, 255, 255, 0.55F),
        divider = Color.rgba(255, 255, 255, 0.06F),
        accent = Color.rgb(0x64C8DC),
        link = Color.rgba(255, 255, 255, 0.5F),
        codeBlock = Color.rgb(41, 42, 45)
    )
}

fun ColorMode.toSitePalette(): SitePalette {
    return when (this) {
        ColorMode.LIGHT -> SitePalettes.light
        ColorMode.DARK -> SitePalettes.dark
    }
}

@InitSilk
fun initTheme(ctx: InitSilkContext) {
    ctx.theme.palettes.light.background = Color.rgb(250, 250, 250)
    ctx.theme.palettes.light.color = Colors.Black
    ctx.theme.palettes.dark.background = Color.rgb(12, 12, 12)
    ctx.theme.palettes.dark.color = Colors.White
}
