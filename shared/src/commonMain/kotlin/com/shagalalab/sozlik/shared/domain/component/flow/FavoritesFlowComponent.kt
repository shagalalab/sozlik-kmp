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
import com.shagalalab.sozlik.shared.domain.component.favorites.FavoritesComponent
import com.shagalalab.sozlik.shared.domain.component.favorites.FavoritesComponentImpl
import com.shagalalab.sozlik.shared.domain.component.translation.TranslationComponent
import com.shagalalab.sozlik.shared.domain.component.translation.TranslationComponentImpl

interface FavoritesFlowComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class FavouritesChild(val component: FavoritesComponent) : Child
        class TranslationChild(val component: TranslationComponent) : Child
    }
}

class FavoritesFlowComponentImpl(componentContext: ComponentContext) : FavoritesFlowComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        initialStack = { listOf(Config.Favorites) },
        handleBackButton = true,
        childFactory = ::child
    )

    override val childStack: Value<ChildStack<*, FavoritesFlowComponent.Child>> = stack

    private fun child(config: Config, componentContext: ComponentContext): FavoritesFlowComponent.Child =
        when (config) {
            Config.Favorites -> FavoritesFlowComponent.Child.FavouritesChild(
                FavoritesComponentImpl(componentContext) {
                    navigation.bringToFront(Config.Translation(it))
                }
            )

            is Config.Translation -> FavoritesFlowComponent.Child.TranslationChild(TranslationComponentImpl(config.id, componentContext) {
                navigation.pop()
            })
        }

    private sealed interface Config : Parcelable {
        @Parcelize
        object Favorites : Config

        @Parcelize
        data class Translation(val id: Long) : Config
    }
}

