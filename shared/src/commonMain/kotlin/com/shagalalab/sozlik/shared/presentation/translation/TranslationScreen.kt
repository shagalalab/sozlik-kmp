package com.shagalalab.sozlik.shared.presentation.translation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shagalalab.sozlik.shared.domain.component.translation.TranslationComponent
import com.shagalalab.sozlik.shared.util.parseHtml

@Composable
fun TranslationScreen(component: TranslationComponent, modifier: Modifier = Modifier) {
    val dictionary by component.state.collectAsState()

    Column(modifier = modifier.padding(16.dp)) {
        dictionary.translation?.let {
            Text(it.rawWord ?: it.word)
            Text(it.type.name)
            Text(it.translation.parseHtml())
        }
    }

    LaunchedEffect(Unit) {
        component.getTranslation()
    }
}
