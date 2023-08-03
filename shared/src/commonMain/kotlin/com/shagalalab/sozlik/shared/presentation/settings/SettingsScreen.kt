package com.shagalalab.sozlik.shared.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shagalalab.sozlik.CommonRes
import com.shagalalab.sozlik.shared.domain.component.settings.SettingsComponent
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SettingsScreen(component: SettingsComponent, modifier: Modifier = Modifier) {
    Column {
        TopAppBar(title = { Text(stringResource(CommonRes.strings.settings)) })
        Text("Settings")
    }
}
