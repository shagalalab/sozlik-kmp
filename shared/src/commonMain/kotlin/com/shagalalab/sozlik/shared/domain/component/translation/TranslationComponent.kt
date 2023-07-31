package com.shagalalab.sozlik.shared.domain.component.translation

import com.arkivanov.decompose.ComponentContext
import com.shagalalab.sozlik.shared.domain.mvi.feature.feature.TranslateAction
import com.shagalalab.sozlik.shared.domain.mvi.feature.feature.TranslationStore
import com.shagalalab.sozlik.shared.domain.mvi.feature.feature.TranslateState
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface TranslationComponent {
    val state: StateFlow<TranslateState>
    fun getTranslation()
    fun onShareClick()
    fun onFavoriteClick()
}

class TranslationComponentImpl(private val translationId: Long, componentContext: ComponentContext) : ComponentContext by componentContext,
    TranslationComponent, KoinComponent {
    private val translationStore: TranslationStore by inject()
    override fun getTranslation() {
        translationStore.dispatch(TranslateAction.GetWordByIdAction(translationId))
    }

    override val state: StateFlow<TranslateState>
        get() = translationStore.stateFlow

    override fun onShareClick() {
        TODO("Not yet implemented")
    }

    override fun onFavoriteClick() {
        TODO("Not yet implemented")
    }

}
