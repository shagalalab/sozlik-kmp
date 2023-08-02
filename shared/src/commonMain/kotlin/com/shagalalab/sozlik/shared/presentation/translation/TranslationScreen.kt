package com.shagalalab.sozlik.shared.presentation.translation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shagalalab.sozlik.shared.domain.component.translation.TranslationComponent
import com.shagalalab.sozlik.shared.presentation.common.FlagFromTo
import com.shagalalab.sozlik.shared.util.parseHtml

@Composable
fun TranslationScreen(component: TranslationComponent, modifier: Modifier = Modifier) {
    val state by component.state.collectAsState()

    Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
        state.translation?.let {
            Row(modifier = modifier.align(alignment = Alignment.End)) {
                IconButton(onClick = component::onFavoriteClick) {
                    Icon(if (state.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder, contentDescription = null)
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Outlined.Share, contentDescription = null)
                }
            }
            Text(it.rawWord ?: it.word)
            FlagFromTo(it.type)
            Text(it.translation.parseHtml())
        }
    }

    LaunchedEffect(Unit) {
        component.getTranslation()
    }
}
