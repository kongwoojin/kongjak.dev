package dev.kongjak.build

import org.gradle.api.logging.Logger
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

private val contributionsTotalRegex = Regex("\"lastYear\"\\s*:\\s*(\\d+)")
private val contributionsDayRegex =
    Regex("\\{\\s*\"date\"\\s*:\\s*\"([^\"]+)\"\\s*,\\s*\"count\"\\s*:\\s*(\\d+)\\s*,\\s*\"level\"\\s*:\\s*(\\d+)\\s*\\}")

fun fetchAndGenerateContributions(
    username: String,
    cacheFile: File,
    cacheTtlMillis: Long,
    outFile: File,
    logger: Logger,
) {
    cacheFile.parentFile.mkdirs()
    val cacheFresh = cacheFile.exists() &&
        (System.currentTimeMillis() - cacheFile.lastModified()) < cacheTtlMillis

    if (!cacheFresh) {
        try {
            val url = URL("https://github-contributions-api.jogruber.de/v4/$username?y=last")
            val conn = url.openConnection() as HttpURLConnection
            conn.connectTimeout = 8000
            conn.readTimeout = 12000
            conn.requestMethod = "GET"
            conn.setRequestProperty("Accept", "application/json")
            conn.setRequestProperty("User-Agent", "kongjak.dev-build")
            val body = conn.inputStream.bufferedReader().use { it.readText() }
            if (conn.responseCode in 200..299 && body.isNotBlank()) {
                cacheFile.writeText(body)
            } else {
                logger.warn("GitHub contributions fetch returned ${conn.responseCode}, falling back to cache")
            }
        } catch (e: Exception) {
            logger.warn("GitHub contributions fetch failed (${e.message}), falling back to cache")
        }
    }

    val json = if (cacheFile.exists()) cacheFile.readText() else ""
    val total = contributionsTotalRegex.find(json)?.groupValues?.get(1)?.toIntOrNull() ?: 0
    val days = contributionsDayRegex
        .findAll(json)
        .map { Triple(it.groupValues[1], it.groupValues[2].toInt(), it.groupValues[3].toInt()) }
        .toList()

    val content = buildString {
        appendLine("package dev.kongjak.kongjak.generated")
        appendLine()
        appendLine("data class GithubContributionDay(val date: String, val count: Int, val level: Int)")
        appendLine()
        appendLine("val githubContributionsTotal: Int = $total")
        appendLine()
        appendLine("val githubContributionsUpdatedMillis: Long = ${if (cacheFile.exists()) cacheFile.lastModified() else 0L}L")
        appendLine()
        appendLine("val githubContributions: List<GithubContributionDay> = listOf(")
        days.forEach { (date, count, level) ->
            appendLine("    GithubContributionDay(\"$date\", $count, $level),")
        }
        appendLine(")")
    }

    outFile.parentFile.mkdirs()
    // Avoid bumping mtime (and therefore invalidating downstream KotlinJS tasks)
    // when the generated file is byte-identical.
    if (!outFile.exists() || outFile.readText() != content) {
        outFile.writeText(content)
    }
    logger.lifecycle("GitHub contributions: total=$total, days=${days.size}, cacheFresh=$cacheFresh")
}

fun resolveGitHeadRefFile(rootDir: File): File? {
    val head = rootDir.resolve(".git/HEAD")
    if (!head.exists()) return null
    val content = head.readText().trim()
    if (!content.startsWith("ref: ")) return null
    val ref = rootDir.resolve(".git").resolve(content.removePrefix("ref: "))
    return ref.takeIf { it.exists() }
}

fun generateBuildInfo(rootDir: File, outFile: File, logger: Logger) {
    val hash = try {
        val proc = ProcessBuilder("git", "rev-parse", "--short", "HEAD")
            .directory(rootDir)
            .redirectErrorStream(true)
            .start()
        val out = proc.inputStream.bufferedReader().use { it.readText() }.trim()
        if (proc.waitFor() == 0 && out.isNotEmpty()) out else "unknown"
    } catch (e: Exception) {
        logger.warn("git rev-parse failed (${e.message}); using \"unknown\"")
        "unknown"
    }

    val content = buildString {
        appendLine("package dev.kongjak.kongjak.generated")
        appendLine()
        appendLine("val gitCommitHash: String = \"$hash\"")
    }

    outFile.parentFile.mkdirs()
    if (!outFile.exists() || outFile.readText() != content) {
        outFile.writeText(content)
    }
}
