package com.shagalalab.sozlik.shared.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
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
import com.shagalalab.sozlik.shared.presentation.favorites.FavoritesScreen
import com.shagalalab.sozlik.shared.presentation.search.SearchScreen
import com.shagalalab.sozlik.shared.presentation.settings.SettingsScreen
import com.shagalalab.sozlik.shared.presentation.translation.TranslationScreen
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
        topBar = {
            TopAppBar(title = { Text(stringResource(CommonRes.strings.app_name)) })
        },
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Search, contentDescription = null) },
                    label = { Text(stringResource(CommonRes.strings.search)) },
                    selected = activeComponent is RootComponent.Child.SearchChild,
                    onClick = component::onSearchTabClicked,
                    enabled = !isLoading
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                    label = { Text(stringResource(CommonRes.strings.favorites)) },
                    selected = activeComponent is RootComponent.Child.FavoritesChild,
                    onClick = component::onFavoritesTabClicked,
                    enabled = !isLoading
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Settings, contentDescription = null) },
                    label = { Text(stringResource(CommonRes.strings.settings)) },
                    selected = activeComponent is RootComponent.Child.SettingsChild,
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
                is RootComponent.Child.SearchChild -> SearchScreen(child.component, padding)
                is RootComponent.Child.FavoritesChild -> FavoritesScreen(child.component, padding)
                is RootComponent.Child.SettingsChild -> SettingsScreen(child.component, padding)
                is RootComponent.Child.TranslationChild -> TranslationScreen(child.component, padding)
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
