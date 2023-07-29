package com.shagalalab.sozlik.shared

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.shagalalab.sozlik.shared.domain.component.root.RootComponent
import com.shagalalab.sozlik.shared.presentation.RootScreen

@Composable
fun App(rootComponent: RootComponent) {
    MaterialTheme {
        RootScreen(rootComponent)
    }
}
