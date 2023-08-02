package com.shagalalab.sozlik.shared.presentation.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shagalalab.sozlik.shared.domain.component.favorites.FavoritesComponent
import com.shagalalab.sozlik.shared.domain.mvi.model.Dictionary

@Composable
fun FavoritesScreen(component: FavoritesComponent, modifier: Modifier = Modifier) {
    val favorites by component.state.collectAsState()
    Column {
        LazyColumn(modifier = modifier) {
            items(favorites.favoriteWords) { word ->
                FavoriteItem(word, component::onFavoriteItemClicked, component::onFavoriteClicked)
            }
        }
    }

    LaunchedEffect(Unit) {
        component.getFavorites()
    }
}

@Composable
private fun FavoriteItem(word: Dictionary, itemClick: (Long) -> Unit, onFavoriteClick: (Long) -> Unit) {
    Column(modifier = Modifier.clickable { itemClick(word.id) }) {
        Row(modifier = Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(word.word, modifier = Modifier.padding(vertical = 12.dp).weight(1f))
            IconButton(onClick = {onFavoriteClick(word.id) }) {
                Icon(Icons.Filled.Favorite, contentDescription = null)
            }
        }
        Divider()
    }
}
