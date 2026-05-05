package dev.kongjak.kongjak.utils

fun slugifyTag(tag: String): String =
    tag
        .trim()
        .lowercase()
        .replace(Regex("[^a-z0-9가-힣]+"), "-")
        .trim('-')

fun parseTagQuery(search: String): String? =
    search
        .removePrefix("?")
        .split('&')
        .firstOrNull { it.startsWith("tag=") }
        ?.substringAfter('=')
        ?.replace("+", " ")
        ?.takeIf { it.isNotBlank() }
