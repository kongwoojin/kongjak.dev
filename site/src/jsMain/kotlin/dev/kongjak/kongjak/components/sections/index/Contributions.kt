package dev.kongjak.kongjak.components.sections.index

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.kongjak.kongjak.generated.githubContributions
import dev.kongjak.kongjak.generated.githubContributionsTotal
import org.jetbrains.compose.web.dom.B
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

private const val WEEKS = 53
private const val DAYS = 7
private const val MS_PER_DAY = 86_400_000.0

private val LEGEND_BACKGROUNDS = listOf(
    "var(--bg-soft)",
    "color-mix(in oklab, var(--accent) 25%, var(--bg-soft))",
    "color-mix(in oklab, var(--accent) 50%, var(--bg-soft))",
    "color-mix(in oklab, var(--accent) 75%, var(--bg-soft))",
    "var(--accent)",
)

@Composable
fun IndexContributions() {
    val heatmap = remember { buildHeatmap() }

    Section(attrs = { id("contributions") }) {
        SectionHead("03", "contributions")

        Div(attrs = { classes("contrib-card", "reveal") }) {
            Div(attrs = { classes("contrib-head") }) {
                Div(attrs = { classes("total") }) {
                    B { Text(formatThousands(heatmap.total)) }
                    Text(" contributions in the last year")
                }
                Div(attrs = { classes("range") }) { Text(heatmap.range) }
            }

            Div(attrs = { classes("contrib-graph") }) {
                for (week in heatmap.weeks) {
                    Div(attrs = { classes("contrib-week") }) {
                        for (cell in week) {
                            if (cell == null) {
                                Div(attrs = {
                                    classes("contrib-day")
                                    style { property("visibility", "hidden") }
                                })
                            } else {
                                Div(attrs = {
                                    classes("contrib-day")
                                    attr("data-l", cell.level.toString())
                                    attr("title", cell.tooltip)
                                })
                            }
                        }
                    }
                }
            }

            Div(attrs = { classes("contrib-legend") }) {
                Text("Less")
                LEGEND_BACKGROUNDS.forEach { bg ->
                    Span(attrs = { classes("sq"); style { property("background", bg) } })
                }
                Text("More")
            }
        }
    }
}

private data class ContribCell(val level: Int, val tooltip: String)

private data class Heatmap(
    val weeks: List<List<ContribCell?>>,
    val total: Int,
    val range: String,
)

private fun epochMsToIso(ms: Double): String {
    val z = (ms / MS_PER_DAY).toLong() + 719468L
    val era = (if (z >= 0L) z else z - 146096L) / 146097L
    val doe = (z - era * 146097L).toInt()
    val yoe = (doe - doe / 1460 + doe / 36524 - doe / 146096) / 365
    val y = yoe + (era * 400L).toInt()
    val doy = doe - (365 * yoe + yoe / 4 - yoe / 100)
    val mp = (5 * doy + 2) / 153
    val d = doy - (153 * mp + 2) / 5 + 1
    val m = if (mp < 10) mp + 3 else mp - 9
    val year = if (m <= 2) y + 1 else y
    return "${year.toString().padStart(4, '0')}-${m.toString().padStart(2, '0')}-${d.toString().padStart(2, '0')}"
}

private fun buildHeatmap(): Heatmap {
    val byDate = githubContributions.associateBy { it.date }

    val todayMs: Double = js("(function(){var d=new Date();d.setHours(0,0,0,0);return d.getTime();})()")
    val startMs = todayMs - (WEEKS * DAYS - 1) * MS_PER_DAY

    val weeks = mutableListOf<List<ContribCell?>>()
    var totalFromGrid = 0

    for (w in 0 until WEEKS) {
        val week = mutableListOf<ContribCell?>()
        for (d in 0 until DAYS) {
            val dayMs = startMs + (w * DAYS + d) * MS_PER_DAY
            if (dayMs > todayMs) {
                week.add(null)
                continue
            }
            val iso = epochMsToIso(dayMs)
            val day = byDate[iso]
            if (day == null) {
                week.add(ContribCell(level = 0, tooltip = "$iso · 0 contributions"))
            } else {
                totalFromGrid += day.count
                week.add(ContribCell(level = day.level, tooltip = "$iso · ${day.count} contributions"))
            }
        }
        weeks.add(week)
    }

    val total = if (githubContributionsTotal > 0) githubContributionsTotal else totalFromGrid
    val startIso = epochMsToIso(startMs).substring(0, 7)
    val endIso = epochMsToIso(todayMs).substring(0, 7)

    return Heatmap(weeks, total, "$startIso → $endIso")
}

private fun formatThousands(n: Int): String {
    val s = n.toString()
    val sb = StringBuilder()
    for ((i, ch) in s.reversed().withIndex()) {
        if (i > 0 && i % 3 == 0) sb.append(',')
        sb.append(ch)
    }
    return sb.reverse().toString()
}
