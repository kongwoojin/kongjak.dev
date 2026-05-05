package dev.kongjak.build

fun String.escapeKotlinString(): String =
    buildString(length) {
        for (char in this@escapeKotlinString) {
            when (char) {
                '\\' -> append("\\\\")
                '"' -> append("\\\"")
                '\n' -> append("\\n")
                '\r' -> append("\\r")
                '\t' -> append("\\t")
                else -> append(char)
            }
        }
    }

fun String.xmlEscape(): String =
    replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("'", "&apos;")

fun unwrapYamlString(value: String): String =
    if (value.length >= 2 && value.startsWith("\"") && value.endsWith("\"")) {
        value.substring(1, value.length - 1)
            .replace("\\\"", "\"")
            .replace("\\\\", "\\")
    } else {
        value.trim('"')
    }

private val titleCaseAcronyms = setOf("cs", "api", "cli", "ui", "os", "io", "ip", "url", "css", "html")

fun slugToTitle(slug: String): String =
    slug
        .split('_', '-')
        .joinToString(" ") { token ->
            if (token.lowercase() in titleCaseAcronyms) token.uppercase()
            else token.replaceFirstChar { it.uppercase() }
        }

fun parseListValue(rawValue: String): List<String> =
    rawValue
        .split(',')
        .map { it.trim() }
        .filter { it.isNotEmpty() }

fun parseSimpleFrontMatter(markdown: String): Map<String, String> {
    if (!markdown.startsWith("---\n") && !markdown.startsWith("---\r\n")) return emptyMap()

    val lines = markdown.lines()
    if (lines.firstOrNull()?.trim() != "---") return emptyMap()

    val result = linkedMapOf<String, String>()
    var currentKey: String? = null
    val currentList = mutableListOf<String>()

    fun flushList() {
        val key = currentKey ?: return
        if (currentList.isNotEmpty()) {
            result[key] = currentList.joinToString(", ")
            currentList.clear()
        }
        currentKey = null
    }

    for (line in lines.drop(1)) {
        val trimmed = line.trim()
        if (trimmed == "---") {
            flushList()
            break
        }

        if (trimmed.startsWith("- ") && currentKey != null) {
            currentList += unwrapYamlString(trimmed.removePrefix("- ").trim())
            continue
        }

        flushList()

        val separatorIndex = line.indexOf(':')
        if (separatorIndex <= 0) continue

        val key = line.substring(0, separatorIndex).trim()
        val rawValue = line.substring(separatorIndex + 1).trim()
        if (rawValue.startsWith("[") && rawValue.endsWith("]")) {
            result[key] = rawValue
                .removePrefix("[")
                .removeSuffix("]")
                .split(',')
                .map { unwrapYamlString(it.trim()) }
                .filter { it.isNotEmpty() }
                .joinToString(", ")
        } else if (rawValue.isEmpty()) {
            currentKey = key
        } else {
            result[key] = unwrapYamlString(rawValue)
        }
    }

    return result
}

fun stripFrontMatter(markdown: String): String {
    if (!markdown.startsWith("---\n") && !markdown.startsWith("---\r\n")) return markdown
    val lines = markdown.lines()
    if (lines.firstOrNull()?.trim() != "---") return markdown

    val endIndex = lines.drop(1).indexOfFirst { it.trim() == "---" }
    return if (endIndex == -1) markdown else lines.drop(endIndex + 2).joinToString("\n")
}

fun inferTitle(markdownBody: String, fallback: String): String =
    markdownBody
        .lineSequence()
        .map { it.trim() }
        .firstOrNull { it.startsWith("# ") }
        ?.removePrefix("# ")
        ?.trim()
        ?.takeIf { it.isNotEmpty() }
        ?: fallback

fun inferDescription(markdownBody: String): String =
    markdownBody
        .lineSequence()
        .map { it.trim() }
        .filter { line ->
            line.isNotEmpty() &&
                !line.startsWith("#") &&
                !line.startsWith("![]") &&
                !line.startsWith("```") &&
                !line.startsWith(">") &&
                !line.startsWith("- ")
        }
        .firstOrNull()
        ?.replace(Regex("\\[(.*?)]\\((.*?)\\)"), "$1")
        ?.take(180)
        .orEmpty()
