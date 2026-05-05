package com.kongjak.kongjak.components.layouts

import com.varabyte.kobweb.compose.foundation.layout.ColumnScope

/**
 * A ColumnScope receiver used by page layouts that emit content directly into
 * a block container (e.g. `<main class="shell">`) instead of a Kobweb `Column`.
 *
 * Kobweb's `Column` defaults to `align-items: flex-start`, which makes children
 * shrink to their content width — undesirable when we want sections to fill
 * the shell's max-width. By using this scope object together with a plain
 * `<main>` wrapper, sections render as standard block children that fill the
 * available width naturally, matching the portfolio design.
 */
internal object PageContentScope : ColumnScope
