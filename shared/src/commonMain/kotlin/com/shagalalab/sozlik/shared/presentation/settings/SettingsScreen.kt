package com.shagalalab.sozlik.shared.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shagalalab.sozlik.CommonRes
import com.shagalalab.sozlik.shared.domain.component.settings.SettingsComponent
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SettingsScreen(component: SettingsComponent, modifier: Modifier = Modifier) {
    Column {
        TopAppBar(title = { Text(stringResource(CommonRes.strings.settings)) })
        SettingsItem(label = stringResource(CommonRes.strings.selected_language), onClick = component::onClickLanguage)
        SettingsItem(label = stringResource(CommonRes.strings.about_us), onClick = component::onClickAboutUs)
        SettingsItem(label = stringResource(CommonRes.strings.share), onClick = component::onClickShare)
    }
}

@Composable
fun SettingsItem(label: String, value: String? = null, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(modifier = modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(if (value.isNullOrEmpty()) label else "$label:")
            value?.let {
                Text(it)
            }
        }
        Divider(modifier = Modifier.fillMaxWidth())
    }
}
