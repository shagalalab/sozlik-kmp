package com.shagalalab.sozlik.shared.presentation.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shagalalab.sozlik.CommonRes
import com.shagalalab.sozlik.shared.domain.component.favorites.FavoritesComponent
import com.shagalalab.sozlik.shared.domain.mvi.model.Dictionary
import dev.icerock.moko.resources.compose.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(component: FavoritesComponent, modifier: Modifier = Modifier) {
    val favorites by component.state.collectAsState()
    Column {
        TopAppBar(title = { Text(stringResource(CommonRes.strings.favorites)) })
        if (favorites.favoriteWords.isEmpty()) {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        Icons.Filled.BookmarkBorder,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.outline),
                        modifier = Modifier.size(100.dp),
                    )
                    Text(text = stringResource(CommonRes.strings.favorites_empty), modifier = Modifier.padding(horizontal = 32.dp), textAlign = TextAlign.Center)
                }
            }
        } else {
            LazyColumn(modifier = modifier) {
                items(favorites.favoriteWords) { word ->
                    FavoriteItem(word, component::onFavoriteItemClicked, component::onFavoriteClicked)
                }
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
            IconButton(onClick = { onFavoriteClick(word.id) }) {
                Icon(Icons.Filled.Bookmark, contentDescription = null)
            }
        }
        Divider()
    }
}
