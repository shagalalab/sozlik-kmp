package com.shagalalab.sozlik.shared.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.shagalalab.sozlik.shared.domain.component.favorites.FavoritesComponent
import com.shagalalab.sozlik.shared.domain.component.favorites.FavoritesComponentImpl
import com.shagalalab.sozlik.shared.domain.component.search.SearchComponent
import com.shagalalab.sozlik.shared.domain.component.search.SearchComponentImpl
import com.shagalalab.sozlik.shared.domain.component.settings.SettingsComponent
import com.shagalalab.sozlik.shared.domain.component.settings.SettingsComponentImpl

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun onSearchTabClicked()
    fun onFavoritesTabClicked()
    fun onSettingsTabClicked()

    sealed interface Child {
        class SearchChild(val component: SearchComponent): Child
        class FavoritesChild(val component: FavoritesComponent): Child
        class SettingsChild(val component: SettingsComponent): Child
    }
}

class RootComponentImpl(componentContext: ComponentContext) : RootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        initialStack = { listOf(Config.Search) },
        childFactory = ::child
    )

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = stack

    override fun onSearchTabClicked() {
        navigation.bringToFront(Config.Search)
    }

    override fun onFavoritesTabClicked() {
        navigation.bringToFront(Config.Favorite)
    }

    override fun onSettingsTabClicked() {
        navigation.bringToFront(Config.Settings)
    }

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            Config.Search -> RootComponent.Child.SearchChild(SearchComponentImpl(componentContext))
            Config.Favorite -> RootComponent.Child.FavoritesChild(FavoritesComponentImpl(componentContext))
            Config.Settings -> RootComponent.Child.SettingsChild(SettingsComponentImpl(componentContext))
        }

    private sealed interface Config : Parcelable {
        @Parcelize
        object Search : Config {
            /**
             * Only required for state preservation on JVM/desktop via StateKeeper, as it uses Serializable.
             * Temporary workaround for https://youtrack.jetbrains.com/issue/KT-40218.
             */
            @Suppress("unused")
            private fun readResolve(): Any = Search
        }

        @Parcelize
        object Favorite : Config {
            /**
             * Only required for state preservation on JVM/desktop via StateKeeper, as it uses Serializable.
             * Temporary workaround for https://youtrack.jetbrains.com/issue/KT-40218.
             */
            @Suppress("unused")
            private fun readResolve(): Any = Favorite
        }

        @Parcelize
        object Settings : Config {
            /**
             * Only required for state preservation on JVM/desktop via StateKeeper, as it uses Serializable.
             * Temporary workaround for https://youtrack.jetbrains.com/issue/KT-40218.
             */
            @Suppress("unused")
            private fun readResolve(): Any = Settings
        }
    }
}