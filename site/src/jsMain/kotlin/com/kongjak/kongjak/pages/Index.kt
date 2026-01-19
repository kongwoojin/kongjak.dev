package com.kongjak.kongjak.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.kongjak.kongjak.components.layouts.PageLayoutData
import com.kongjak.kongjak.components.sections.index.IndexMain
import com.kongjak.kongjak.components.sections.index.IndexProjects
import com.kongjak.kongjak.components.sections.index.IndexSkills

@InitRoute
fun initHomePage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Home"))
}

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun HomePage() {
    IndexMain()
    IndexSkills()
    IndexProjects()
}
