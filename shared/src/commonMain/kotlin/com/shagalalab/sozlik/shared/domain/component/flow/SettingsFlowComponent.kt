package com.shagalalab.sozlik.shared.domain.component.flow

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.shagalalab.sozlik.shared.domain.component.settings.SettingsComponent
import com.shagalalab.sozlik.shared.domain.component.settings.SettingsComponentImpl

interface SettingsFlowComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class SettingsChild(val component: SettingsComponent) : Child
    }
}

class SettingsFlowComponentImpl(componentContext: ComponentContext) : SettingsFlowComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        initialStack = { listOf(Config.Settings) },
        handleBackButton = true,
        childFactory = ::child
    )

    override val childStack: Value<ChildStack<*, SettingsFlowComponent.Child>> = stack

    private fun child(config: Config, componentContext: ComponentContext): SettingsFlowComponent.Child =
        when (config) {
            Config.Settings -> SettingsFlowComponent.Child.SettingsChild(
                SettingsComponentImpl(componentContext)
            )
        }

    private sealed interface Config : Parcelable {
        @Parcelize
        object Settings : Config
    }
}
