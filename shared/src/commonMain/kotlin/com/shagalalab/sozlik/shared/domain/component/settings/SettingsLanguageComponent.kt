package com.shagalalab.sozlik.shared.domain.component.settings

import com.arkivanov.decompose.ComponentContext

interface SettingsLanguageComponent {
    val layoutOptions: List<String>
    val defaultOption: String
    fun onDismissClicked()
    fun onOptionSelected(option: String)
}

class SettingsLanguageComponentImpl(
    private val componentContext: ComponentContext,
    override val layoutOptions: List<String>,
    override val defaultOption: String,
    private val onOptionChanged: (String) -> Unit,
    private val onDismissed: () -> Unit,
) : SettingsLanguageComponent, ComponentContext by componentContext {

    override fun onDismissClicked() {
        onDismissed()
    }

    override fun onOptionSelected(option: String) {
        onOptionChanged(option)
    }
}
