package com.shagalalab.sozlik.shared.domain.mvi.feature.search

import com.shagalalab.sozlik.shared.domain.mvi.base.Action
import com.shagalalab.sozlik.shared.domain.mvi.base.Effect
import com.shagalalab.sozlik.shared.domain.mvi.base.State
import com.shagalalab.sozlik.shared.domain.mvi.model.DictionaryBase
import dev.icerock.moko.resources.desc.ResourceFormattedStringDesc

sealed interface SearchAction : Action {
    data class SearchWordAction(val query: String): SearchAction
}

data class SearchState(
    val isLoading: Boolean = false,
    val query: String = "",
    val suggestions: List<DictionaryBase> = listOf(),
    val message: ResourceFormattedStringDesc? = null,
    val errorMessage: String? = null,
) : State

interface SearchEffect : Effect
