package com.shagalalab.sozlik.shared.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.shagalalab.sozlik.CommonRes
import com.shagalalab.sozlik.shared.domain.component.root.RootComponent
import com.shagalalab.sozlik.shared.presentation.flow.FavoritesFlowScreen
import com.shagalalab.sozlik.shared.presentation.flow.SearchFlowScreen
import com.shagalalab.sozlik.shared.presentation.flow.SettingsFlowScreen
import dev.icerock.moko.resources.compose.readTextAsState
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun RootScreen(component: RootComponent) {
    val childStack by component.childStack.subscribeAsState()
    val isLoading by component.isLoading.collectAsState(false)
    val qqEn: String? by CommonRes.files.qqen.readTextAsState()
    val ruQq: String? by CommonRes.files.ruqq.readTextAsState()
    val activeComponent = childStack.active.instance

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Search, contentDescription = null) },
                    label = { Text(stringResource(CommonRes.strings.search)) },
                    selected = activeComponent is RootComponent.Child.SearchFlowChild,
                    onClick = component::onSearchTabClicked,
                    enabled = !isLoading
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Bookmark, contentDescription = null) },
                    label = { Text(stringResource(CommonRes.strings.favorites)) },
                    selected = activeComponent is RootComponent.Child.FavoritesFlowChild,
                    onClick = component::onFavoritesTabClicked,
                    enabled = !isLoading
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Settings, contentDescription = null) },
                    label = { Text(stringResource(CommonRes.strings.settings)) },
                    selected = activeComponent is RootComponent.Child.SettingsFlowChild,
                    onClick = component::onSettingsTabClicked,
                    enabled = !isLoading
                )
            }
        }
    ) { paddingValue ->
        val padding = Modifier.padding(paddingValue)

        Children(
            stack = childStack
        ) {
            when (val child = it.instance) {
                is RootComponent.Child.SearchFlowChild -> SearchFlowScreen(child.component, padding)
                is RootComponent.Child.FavoritesFlowChild -> FavoritesFlowScreen(child.component, padding)
                is RootComponent.Child.SettingsFlowChild -> SettingsFlowScreen(child.component, padding)
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(Color.LightGray.copy(alpha = 0.7f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        LaunchedEffect(Unit) {
            component.checkDbPopulated(qqEn, ruQq)
        }
    }
}
