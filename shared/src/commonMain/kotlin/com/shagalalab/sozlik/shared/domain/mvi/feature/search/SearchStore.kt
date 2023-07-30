package com.shagalalab.sozlik.shared.domain.mvi.feature.search

import com.shagalalab.sozlik.shared.domain.mvi.base.Store
import com.shagalalab.sozlik.shared.domain.repository.DictionaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.koin.core.component.KoinComponent

class SearchStore(
    private val repository: DictionaryRepository,
    scope: CoroutineScope = MainScope()
) : Store<SearchAction, SearchState, SearchEffect>(SearchState(), scope), KoinComponent {

    override suspend fun reduce(action: SearchAction, childScope: CoroutineScope): SearchState {
        val oldState = stateFlow.value
        return when (action) {
            is SearchAction.SearchWordAction -> {
                emitState(oldState.copy(isLoading = true))
                try {
                    val result = repository.getSuggestions(action.query)
                    when {
                        result.isSuccess -> {
                            oldState.copy(isLoading = false, query = action.query, suggestions = result.getOrDefault(listOf()))
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
        }
    }
}
