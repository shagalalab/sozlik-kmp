package com.shagalalab.sozlik.shared.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.shagalalab.sozlik.CommonRes
import com.shagalalab.sozlik.shared.domain.component.settings.SettingsComponent
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SettingsScreen(component: SettingsComponent, modifier: Modifier = Modifier) {
    Column(modifier) {
        TopAppBar(title = { Text(stringResource(CommonRes.strings.settings)) })
        SettingsItem(Icons.Outlined.List, stringResource(CommonRes.strings.selected_language), value = stringResource(CommonRes.strings.layout_latin), onClick = component::onClickLanguage)
        SettingsItem(Icons.Outlined.Info, stringResource(CommonRes.strings.about_us), onClick = component::onClickAboutUs)
        SettingsItem(Icons.Outlined.Share, stringResource(CommonRes.strings.share), onClick = component::onClickShare)
    }
}

@Composable
private fun SettingsItem(icon: ImageVector, label: String, value: String = "", modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(modifier = modifier.clickable(onClick = onClick)) {
        Row(
            modifier = Modifier.fillMaxWidth().height(56.dp).padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null)
            Text(label, modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 16.dp))
            if (value.isNotEmpty()) {
                Text(value)
            }
        }
        Divider(modifier = Modifier.fillMaxWidth())
    }
}
