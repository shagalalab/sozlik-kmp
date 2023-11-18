package com.shagalalab.sozlik.shared.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.shagalalab.sozlik.CommonRes
import com.shagalalab.sozlik.shared.domain.component.settings.SettingsComponent
import com.shagalalab.sozlik.shared.domain.component.settings.about.SettingsAboutComponent
import com.shagalalab.sozlik.shared.domain.component.settings.layout.SettingsLayoutComponent
import com.shagalalab.sozlik.shared.util.isSettingsShareEnabled
import com.shagalalab.sozlik.shared.util.parseHtml
import dev.icerock.moko.resources.compose.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(component: SettingsComponent, modifier: Modifier = Modifier) {
    var selectedOption by remember { mutableStateOf(component.defaultLayout) }
    val dialogSlot by component.dialogSlot.subscribeAsState()

    Box(modifier) {
        Column(modifier) {
            TopAppBar(title = { Text(stringResource(CommonRes.strings.settings)) })
            SettingsItem(
                Icons.Outlined.Translate,
                stringResource(CommonRes.strings.selected_language),
                value = selectedOption,
                onClick = component::onClickLayout
            )
            SettingsItem(Icons.Outlined.Info, stringResource(CommonRes.strings.about), onClick = component::onClickAbout)
            if (isSettingsShareEnabled) {
                SettingsItem(Icons.Outlined.Share, stringResource(CommonRes.strings.share), onClick = component::onClickShare)
            }
        }

        dialogSlot.child?.instance?.also { itemComponent ->
            when (itemComponent) {
                is SettingsLayoutComponent -> {
                    SelectLayoutDialog(itemComponent, selectedOption) {
                        selectedOption = it
                    }
                }
                is SettingsAboutComponent -> {
                    SelectAboutDialog(itemComponent)
                }
            }
        }
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

@Composable
fun SelectLayoutDialog(component: SettingsLayoutComponent, selectedOption: String, onSelectedOptionChanged: (String) -> Unit) {
    AlertDialog(
        onDismissRequest = { component.onDismissClicked() },
        confirmButton = {},
        title = {
            Text(text = stringResource(CommonRes.strings.select_app_layout))
        },
        text = {
            Column(Modifier.selectableGroup()) {
                component.layoutOptions.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = {
                                    onSelectedOptionChanged(text)
                                    component.onDismissClicked()
                                    component.onOptionSelected(text)
                                },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = null // null recommended for accessibility with screenreaders
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .fillMaxWidth()
                        )
                    }
                }
                Text(text = stringResource(CommonRes.strings.select_app_layout_description))
            }
        }
    )
}

@Composable
fun SelectAboutDialog(component: SettingsAboutComponent) {
    val uriHandler = LocalUriHandler.current
    val shagalalabUrl = "www.shagalalab.com"

    AlertDialog(
        onDismissRequest = { component.onDismissClicked() },
        confirmButton = {},
        title = {
            Text(text = stringResource(CommonRes.strings.about))
        },
        text = {
            val parsedAboutString = stringResource(CommonRes.strings.about_content).parseHtml()
            val annotatedAboutStringWithUrl = buildAnnotatedString {
                append(parsedAboutString)
                // attach a string annotation that stores a URL to the text "Jetpack Compose".
                addStringAnnotation(
                    tag = "URL",
                    annotation = "https://$shagalalabUrl",
                    start = parsedAboutString.indexOf(shagalalabUrl),
                    end = parsedAboutString.indexOf(shagalalabUrl) + shagalalabUrl.length
                )
            }

            ClickableText(annotatedAboutStringWithUrl) { offset ->
                annotatedAboutStringWithUrl
                    .getStringAnnotations("URL", offset, offset)
                    .firstOrNull()?.let { stringAnnotation ->
                        uriHandler.openUri(stringAnnotation.item)
                    }
            }
        }
    )
}
