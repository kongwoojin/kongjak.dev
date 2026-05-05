package dev.kongjak.kongjak.pages

import androidx.compose.runtime.Composable
import dev.kongjak.kongjak.components.layouts.PageLayoutData
import dev.kongjak.kongjak.components.sections.index.IndexContact
import dev.kongjak.kongjak.components.sections.index.IndexContributions
import dev.kongjak.kongjak.components.sections.index.IndexMain
import dev.kongjak.kongjak.components.sections.index.IndexProjects
import dev.kongjak.kongjak.components.sections.index.IndexSkills
import dev.kongjak.kongjak.components.sections.index.IndexWriting
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout

@InitRoute
fun initHomePage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Home"))
}

@Page
@Layout(".components.layouts.HomeLayout")
@Composable
fun HomePage() {
    IndexMain()
    IndexSkills()
    IndexProjects()
    IndexContributions()
    IndexWriting()
    IndexContact()
}
