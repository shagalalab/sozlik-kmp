package com.shagalalab.sozlik.shared.domain.mvi.feature.translate

import com.shagalalab.sozlik.shared.domain.mvi.base.Store
import com.shagalalab.sozlik.shared.domain.repository.DictionaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.koin.core.component.KoinComponent

class TranslationStore(
    private val repository: DictionaryRepository,
    scope: CoroutineScope = MainScope()
) : Store<TranslateAction, TranslateState, TranslateEffect>(TranslateState(), scope), KoinComponent
{
    override suspend fun reduce(action: TranslateAction, childScope: CoroutineScope): TranslateState {
        val oldState = stateFlow.value
        return when(action) {
            is TranslateAction.GetWordByIdAction -> {
                emitState(oldState.copy(isLoading = true))
                try {
                    val result = repository.getTranslation(action.id)
                    when {
                        result.isSuccess -> {
                            val dictionary = result.getOrDefault(null)
                            oldState.copy(isLoading = false, translation = dictionary, isFavorite = dictionary?.isFavorite ?: false)
                        }
                        result.isFailure -> {
                            oldState.copy(isLoading = false, errorMessage = "Error: ${result.exceptionOrNull()}")
                        }
                        else -> oldState
                    }
                } catch (e: Exception) {
                    oldState.copy(isLoading = false, errorMessage = "Error: $e")
                }
            }
            is TranslateAction.FavoriteWordAction -> {
                try {
                    repository.updateFavorite(action.id, !oldState.isFavorite)
                    oldState
                } catch (e: Exception) {
                    oldState.copy(isLoading = false, errorMessage = "Error: $e")
                }
            }
        }
    }

}
