package com.shagalalab.sozlik.shared.domain.component.settings

import com.arkivanov.decompose.ComponentContext

interface SettingsComponent {
}

class SettingsComponentImpl(componentContext: ComponentContext) :
    ComponentContext by componentContext,
    SettingsComponent {

}