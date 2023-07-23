package com.shagalalab.sozlik.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.myapplication.common.CommonRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun App(searchComponent: SearchComponent) {
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(CommonRes.string.app_name) })
            },
            bottomBar = {
                var currentTab by remember { mutableStateOf(CommonRes.string.search) }
                BottomNavigation {
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Search, contentDescription = null) },
                        label = { Text(CommonRes.string.search) },
                        selected = currentTab == CommonRes.string.search,
                        onClick = {
                            currentTab = CommonRes.string.search
                        }
                    )
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                        label = { Text(CommonRes.string.favorites) },
                        selected = currentTab == CommonRes.string.favorites,
                        onClick = {
                            currentTab = CommonRes.string.favorites
                        }
                    )
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Settings, contentDescription = null) },
                        label = { Text(CommonRes.string.settings) },
                        selected = currentTab == CommonRes.string.settings,
                        onClick = {
                            currentTab = CommonRes.string.settings
                        }
                    )
                }
            }
        ) {
            SearchScreen(searchComponent)
        }
    }
}

interface SearchComponent {
    val query: StateFlow<String>
    fun onQueryChanged(query: String)
    fun onSearchClicked()
    fun onSearchItemClicked(itemId: String)
}

class RealSearchComponent(componentContext: ComponentContext) : ComponentContext by componentContext,
    SearchComponent {
    override val query = MutableStateFlow("")

    override fun onQueryChanged(query: String) {
        this.query.value = query
    }

    override fun onSearchClicked() {
        println("mytest, search clicked with $query")
    }

    override fun onSearchItemClicked(itemId: String) {
        println("mytest, search clicked with $query")
    }
}

@Composable
fun SearchScreen(component: SearchComponent) {
    val query by component.query.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = component::onQueryChanged
        )
        Button(onClick = { component.onSearchClicked() }) {
            Text("Search")
        }
    }
}
