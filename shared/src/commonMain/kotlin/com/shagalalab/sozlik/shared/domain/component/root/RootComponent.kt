package com.shagalalab.sozlik.shared.domain.component.root

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
import com.shagalalab.sozlik.shared.domain.component.search.SearchComponent
import com.shagalalab.sozlik.shared.domain.component.search.SearchComponentImpl
import com.shagalalab.sozlik.shared.domain.component.settings.SettingsComponent
import com.shagalalab.sozlik.shared.domain.component.settings.SettingsComponentImpl
import com.shagalalab.sozlik.shared.domain.component.translation.TranslationComponent
import com.shagalalab.sozlik.shared.domain.component.translation.TranslationComponentImpl
import com.shagalalab.sozlik.shared.domain.mvi.feature.populate.DbPopulateAction
import com.shagalalab.sozlik.shared.domain.mvi.feature.populate.DbPopulateStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>
    val isLoading: Flow<Boolean>

    fun onSearchTabClicked()
    fun onFavoritesTabClicked()
    fun onSettingsTabClicked()
    fun checkDbPopulated(qqen: String?, ruqq: String?)

    sealed interface Child {
        class SearchChild(val component: SearchComponent) : Child
        class FavoritesChild(val component: FavoritesComponent) : Child
        class SettingsChild(val component: SettingsComponent) : Child
        class TranslationChild(val component: TranslationComponent) : Child
    }
}

class RootComponentImpl(componentContext: ComponentContext) : RootComponent, KoinComponent, ComponentContext by componentContext {
    private val dbPopulateStore: DbPopulateStore by inject()
    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        initialStack = { listOf(Config.Search) },
        handleBackButton = true,
        childFactory = ::child
    )

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = stack

    override val isLoading: Flow<Boolean> = dbPopulateStore.stateFlow.map { it.isLoading }

    override fun onSearchTabClicked() {
        navigation.bringToFront(Config.Search)
    }

    override fun onFavoritesTabClicked() {
        navigation.bringToFront(Config.Favorite)
    }

    override fun onSettingsTabClicked() {
        navigation.bringToFront(Config.Settings)
    }

    override fun checkDbPopulated(qqen: String?, ruqq: String?) {
        dbPopulateStore.dispatch(DbPopulateAction.DbPopulateCheck(qqen, ruqq))
    }

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            Config.Search -> RootComponent.Child.SearchChild(
                SearchComponentImpl(componentContext) {
                    navigation.bringToFront(Config.Translation(it))
                }
            )

            Config.Favorite -> RootComponent.Child.FavoritesChild(
                FavoritesComponentImpl(componentContext) {
                    navigation.bringToFront(Config.Translation(it))
                })

            Config.Settings -> RootComponent.Child.SettingsChild(SettingsComponentImpl(componentContext))
            is Config.Translation -> RootComponent.Child.TranslationChild(TranslationComponentImpl(config.id, componentContext) {
                navigation.pop()
            })
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

        @Parcelize
        data class Translation(val id: Long) : Config
    }
}
