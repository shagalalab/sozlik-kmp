package com.shagalalab.sozlik.shared.domain.component.settings

import com.arkivanov.decompose.ComponentContext

interface SettingsComponent {
    fun onClickShare()
    fun onClickAboutUs()
    fun onClickLanguage()
}

class SettingsComponentImpl(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, SettingsComponent {

    override fun onClickShare() {
        // TODO("Not yet implemented")
    }

    override fun onClickAboutUs() {
        // TODO("Not yet implemented")
    }

    override fun onClickLanguage() {
        // TODO("Not yet implemented")
    }
}
