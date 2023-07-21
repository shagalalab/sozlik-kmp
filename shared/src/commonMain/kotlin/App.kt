
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun App() {
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Sózlik") })
            },
            bottomBar = {
                var currentTab by remember { mutableStateOf("Home") }
                BottomNavigation {
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                        label = { Text("Home") },
                        selected = currentTab == "Home",
                        onClick = {
                            currentTab = "Home"
                        }
                    )
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                        label = { Text("Favourites") },
                        selected = currentTab == "Favourites",
                        onClick = {
                            currentTab = "Favourites"
                        }
                    )
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Settings, contentDescription = null) },
                        label = { Text("Settings") },
                        selected = currentTab == "Settings",
                        onClick = {
                            currentTab = "Settings"
                        }
                    )
                }
            }
        ) {
            Text("Home")
        }
    }
}

expect fun getPlatformName(): String