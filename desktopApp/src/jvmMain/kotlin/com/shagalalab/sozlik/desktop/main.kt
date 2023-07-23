package com.shagalalab.audiokitap.shared.com.shagalalab.sozlik.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.shagalalab.sozlik.shared.MainView
import com.shagalalab.sozlik.shared.RealSearchComponent

fun main() {
    val lifecycle = LifecycleRegistry()
    // Create the root component on the UI thread before starting Compose
    val rootComponent = RealSearchComponent(componentContext = DefaultComponentContext(lifecycle))

    application {
        Window(onCloseRequest = ::exitApplication) {
            MainView(rootComponent)
        }
    }
}
