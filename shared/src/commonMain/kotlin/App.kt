
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.myapplication.common.CommonRes

@Composable
fun App() {
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
            Text("Home")
        }
    }
}

expect fun getPlatformName(): String