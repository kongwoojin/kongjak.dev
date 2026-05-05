package dev.kongjak.kongjak.models

data class Skill(
    val name: String,
    val iconName: String,
) {
    companion object {
        private const val SIMPLE_ICONS_CDN = "https://cdn.simpleicons.org"
    }

    fun getIconPath(isLightMode: Boolean): String {
        val color = if (isLightMode) "000000" else "FFFFFF"
        return "${SIMPLE_ICONS_CDN}/$iconName/$color"
    }
}
