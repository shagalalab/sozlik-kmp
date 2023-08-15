package com.shagalalab.sozlik.shared.domain.mvi.feature.search

import com.shagalalab.sozlik.shared.domain.mvi.base.Store
import com.shagalalab.sozlik.shared.domain.repository.DictionaryRepository
import com.shagalalab.sozlik.shared.domain.spelchecker.SpellChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.koin.core.component.KoinComponent

class SearchStore(
    private val repository: DictionaryRepository,
    private val spellChecker: SpellChecker,
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
                        val result = repository.getSuggestions(action.query)
                        when {
                            result.isSuccess -> {
                                val searchResult = result.getOrDefault(listOf())
                                if (searchResult.isNotEmpty()) {
                                    oldState.copy(isLoading = false, query = action.query, suggestions = searchResult, isSuggested = false)
                                } else {
                                    val isLatin = SpellChecker.ALPHABET_LATIN.contains(action.query.first())
                                    val suggestedResult = spellChecker.check(word = action.query, isLatin)
                                    oldState.copy(
                                        isLoading = false,
                                        query = action.query,
                                        suggestions = suggestedResult,
                                        isSuggested = action.query.isNotEmpty()
                                    )
                                }
                            }

                            result.isFailure -> {
                                oldState.copy(isLoading = false, errorMessage = "Error: ${result.exceptionOrNull()}", isSuggested = false)
                            }

                            else -> oldState.copy(isSuggested = false)
                        }
                    } catch (e: Exception) {
                        oldState.copy(isLoading = false, errorMessage = "Error: $e", isSuggested = false)
                    }
                }
            }
        }
    }
}
