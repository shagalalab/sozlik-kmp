package com.shagalalab.sozlik.shared.domain.component.translation

import com.arkivanov.decompose.ComponentContext
import com.shagalalab.sozlik.shared.domain.mvi.feature.translate.TranslateAction
import com.shagalalab.sozlik.shared.domain.mvi.feature.translate.TranslateState
import com.shagalalab.sozlik.shared.domain.mvi.feature.translate.TranslationStore
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface TranslationComponent {
    val state: StateFlow<TranslateState>

    fun getTranslation()
    fun onShareClick()
    fun onFavoriteClick()
}

class TranslationComponentImpl(
    private val translationId: Long,
    componentContext: ComponentContext
) : ComponentContext by componentContext, TranslationComponent, KoinComponent {
    private val translationStore: TranslationStore by inject()

    override val state: StateFlow<TranslateState> = translationStore.stateFlow

    override fun getTranslation() {
        translationStore.dispatch(TranslateAction.GetWordByIdAction(translationId))
    }

    override fun onShareClick() {
        TODO("Not yet implemented")
    }

    override fun onFavoriteClick() {
        translationStore.dispatch(TranslateAction.FavoriteWordAction(translationId))
        getTranslation()
    }
}
