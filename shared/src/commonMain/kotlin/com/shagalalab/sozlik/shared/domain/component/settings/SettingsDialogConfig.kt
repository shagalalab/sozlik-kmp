package com.shagalalab.sozlik.shared.domain.component.settings

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class SettingsDialogConfig(
    val type: SettingsDialogType,
    val data: SettingsDialogData? = null
) : Parcelable

@Parcelize
enum class SettingsDialogType : Parcelable {
    LAYOUT,
    ABOUT,
}

@Parcelize
sealed interface SettingsDialogData : Parcelable {
    data class SettingsLayoutData(val options: List<String>, val defaultOption: String) : SettingsDialogData
}
