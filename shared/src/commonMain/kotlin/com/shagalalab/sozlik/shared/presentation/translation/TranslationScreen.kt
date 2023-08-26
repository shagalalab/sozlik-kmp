package com.shagalalab.sozlik.shared.presentation.translation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shagalalab.sozlik.shared.domain.component.translation.TranslationComponent
import com.shagalalab.sozlik.shared.presentation.common.FlagFromTo
import com.shagalalab.sozlik.shared.util.parseHtml

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranslationScreen(component: TranslationComponent, modifier: Modifier = Modifier) {
    val state by component.state.collectAsState()

    Column {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = component::onBackButtonPress) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
            title = {},
            actions = {
                IconButton(onClick = component::onFavoriteClick) {
                    Icon(if (state.isFavorite) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder, contentDescription = null)
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Outlined.Share, contentDescription = null)
                }
            }
        )
        Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
            state.translation?.let {
                Text(it.rawWord ?: it.word)
                FlagFromTo(it.type)
                Text(it.translation.parseHtml())
            }
        }
    }

    LaunchedEffect(Unit) {
        component.getTranslation()
    }
}
