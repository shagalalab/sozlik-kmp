package com.shagalalab.sozlik.shared.domain.component.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.shagalalab.sozlik.shared.domain.repository.SettingsRepository
import dev.icerock.moko.resources.desc.StringDesc
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface SettingsComponent {
    val defaultLayout: String
    val dialogSlot: Value<ChildSlot<*, SettingsLanguageComponent>>

    fun onClickLayout()
    fun onClickAboutUs()
    fun onClickShare()
}

class SettingsComponentImpl(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, SettingsComponent, KoinComponent {
    private val settingsRepository: SettingsRepository by inject()
    private val dialogNavigation = SlotNavigation<DialogConfig>()

    override val defaultLayout by lazy { settingsRepository.getSelectedLayoutOption() }
    override val dialogSlot: Value<ChildSlot<*, SettingsLanguageComponent>> =
        childSlot(
            source = dialogNavigation,
            // persistent = false, // Disable navigation state saving, if needed
            handleBackButton = true, // Close the dialog on back button press
        ) { config, childComponentContext ->
            SettingsLanguageComponentImpl(
                componentContext = childComponentContext,
                layoutOptions = config.options,
                defaultOption = config.defaultOption,
                onOptionChanged = { updatedOption ->
                    val previousOption = settingsRepository.getSelectedLayoutOption()
                    if (updatedOption != previousOption) {
                        val locale = settingsRepository.updateSelectedLayoutOption(updatedOption)
                        StringDesc.localeType = StringDesc.LocaleType.Custom(locale)
                    }
                },
                onDismissed = dialogNavigation::dismiss,
            )
        }

    override fun onClickLayout() {
        dialogNavigation.activate(
            DialogConfig(
                options = settingsRepository.getLayoutOptions(),
                defaultOption = settingsRepository.getSelectedLayoutOption()
            )
        )
    }

    override fun onClickShare() {
        // TODO("Not yet implemented")
    }

    override fun onClickAboutUs() {
        // TODO("Not yet implemented")
    }

    @Parcelize
    data class DialogConfig(
        val options: List<String>,
        val defaultOption: String,
    ) : Parcelable
}
