package com.shagalalab.sozlik.shared.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.myapplication.common.CommonRes
import com.shagalalab.sozlik.shared.domain.component.RootComponent
import com.shagalalab.sozlik.shared.presentation.favorites.FavoritesScreen
import com.shagalalab.sozlik.shared.presentation.search.SearchScreen
import com.shagalalab.sozlik.shared.presentation.settings.SettingsScreen

@Composable
fun RootScreen(component: RootComponent) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(CommonRes.string.app_name) })
        },
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Search, contentDescription = null) },
                    label = { Text(CommonRes.string.search) },
                    selected = activeComponent is RootComponent.Child.SearchChild,
                    onClick = component::onSearchTabClicked
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                    label = { Text(CommonRes.string.favorites) },
                    selected = activeComponent is RootComponent.Child.FavoritesChild,
                    onClick = component::onFavoritesTabClicked
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Settings, contentDescription = null) },
                    label = { Text(CommonRes.string.settings) },
                    selected = activeComponent is RootComponent.Child.SettingsChild,
                    onClick = component::onSettingsTabClicked
                )
            }
        }
    ) { paddingValue ->
        val padding = Modifier.padding(paddingValue)

        Children(
            stack = childStack
        ) {
            when (val child = it.instance) {
                is RootComponent.Child.SearchChild -> SearchScreen(child.component, padding)
                is RootComponent.Child.FavoritesChild -> FavoritesScreen(child.component, padding)
                is RootComponent.Child.SettingsChild -> SettingsScreen(child.component, padding)
            }
        }
    }
}
