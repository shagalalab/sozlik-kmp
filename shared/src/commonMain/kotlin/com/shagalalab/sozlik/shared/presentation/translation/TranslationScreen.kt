package com.shagalalab.sozlik.shared.presentation.translation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.shagalalab.sozlik.shared.domain.component.translation.TranslationComponent
import com.shagalalab.sozlik.shared.util.parseHtml

@Composable
fun TranslationScreen(component: TranslationComponent, modifier: Modifier = Modifier) {
    val dictionary by component.state.collectAsState()
    Column(modifier = modifier) {
        dictionary.translation?.let { Text(it.word.parseHtml()) }
        dictionary.translation?.let { Text(it.translation.parseHtml()) }
    }
    LaunchedEffect(Unit) {
        component.getTranslation()
    }
}
