package com.shagalalab.sozlik.shared.domain.mvi.feature.search

import com.shagalalab.sozlik.CommonRes
import com.shagalalab.sozlik.shared.domain.mvi.base.Store
import com.shagalalab.sozlik.shared.domain.repository.DictionaryRepository
import dev.icerock.moko.resources.format
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
                if (action.query.isEmpty()) {
                    SearchState()
                } else {
                    try {
                        val result = repository.searchExact(action.query)
                        when {
                            result.isSuccess -> {
                                val searchResult = result.getOrDefault(listOf())
                                if (searchResult.isNotEmpty()) {
                                    oldState.copy(
                                        isLoading = false,
                                        query = action.query,
                                        suggestions = searchResult,
                                        message = null
                                    )
                                } else {
                                    val suggestedResult = repository.searchSimilar(word = action.query)
                                    oldState.copy(
                                        isLoading = false,
                                        query = action.query,
                                        suggestions = suggestedResult,
                                        message = if (suggestedResult.isEmpty()) {
                                            CommonRes.strings.suggestion_not_found.format(action.query)
                                        } else {
                                            CommonRes.strings.suggestion_found.format(action.query)
                                        }
                                    )
                                }
                            }

                            result.isFailure -> {
                                oldState.copy(isLoading = false, errorMessage = "Error: ${result.exceptionOrNull()}", message = null)
                            }

                            else -> oldState.copy(message = null)
                        }
                    } catch (e: Exception) {
                        oldState.copy(isLoading = false, errorMessage = "Error: $e", message = null)
                    }
                }
            }
        }
    }
}
