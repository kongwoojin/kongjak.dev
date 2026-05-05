package dev.kongjak.kongjak.utils

private fun parts(isoDate: String): List<String>? =
    isoDate.split('-').takeIf { it.size >= 3 }

fun extractYear(isoDate: String): String =
    isoDate.take(4).takeIf { it.length == 4 && it.all(Char::isDigit) } ?: "—"

fun formatShortDate(isoDate: String): String =
    parts(isoDate)?.let { "${it[1]}.${it[2]}" } ?: isoDate

fun formatLongDate(isoDate: String): String =
    parts(isoDate)?.let { "${it[0]}.${it[1]}.${it[2]}" } ?: isoDate
