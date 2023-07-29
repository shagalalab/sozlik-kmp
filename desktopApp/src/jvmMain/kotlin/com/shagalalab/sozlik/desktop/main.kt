package com.shagalalab.sozlik.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.shagalalab.sozlik.shared.MainView
import com.shagalalab.sozlik.shared.di.initKoin
import com.shagalalab.sozlik.shared.domain.component.root.RootComponentImpl

fun main() {
    initKoin {  }

    val lifecycle = LifecycleRegistry()
    // Create the root component on the UI thread before starting Compose
    val rootComponent = RootComponentImpl(componentContext = DefaultComponentContext(lifecycle))

    application {
        Window(onCloseRequest = ::exitApplication) {
            MainView(rootComponent)
        }
    }
}
