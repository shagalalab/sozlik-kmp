package com.shagalalab.sozlik.shared.domain.component.flow

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.shagalalab.sozlik.shared.domain.component.search.SearchComponent
import com.shagalalab.sozlik.shared.domain.component.search.SearchComponentImpl
import com.shagalalab.sozlik.shared.domain.component.translation.TranslationComponent
import com.shagalalab.sozlik.shared.domain.component.translation.TranslationComponentImpl

interface SearchFlowComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class SearchChild(val component: SearchComponent) : Child
        class TranslationChild(val component: TranslationComponent) : Child
    }
}

class SearchFlowComponentImpl(componentContext: ComponentContext) : SearchFlowComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        initialStack = { listOf(Config.Search) },
        handleBackButton = true,
        childFactory = ::child
    )

    override val childStack: Value<ChildStack<*, SearchFlowComponent.Child>> = stack

    private fun child(config: Config, componentContext: ComponentContext): SearchFlowComponent.Child =
        when (config) {
            Config.Search -> SearchFlowComponent.Child.SearchChild(
                SearchComponentImpl(componentContext) {
                    navigation.bringToFront(Config.Translation(it))
                }
            )

            is Config.Translation -> SearchFlowComponent.Child.TranslationChild(TranslationComponentImpl(config.id, componentContext) {
                navigation.pop()
            })
        }

    private sealed interface Config : Parcelable {
        @Parcelize
        object Search : Config

        @Parcelize
        data class Translation(val id: Long) : Config
    }
}
