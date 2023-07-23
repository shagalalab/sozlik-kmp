package com.shagalalab.sozlik.shared.domain.component.search

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface SearchComponent {
    val query: StateFlow<String>
    fun onQueryChanged(query: String)
    fun onSearchClicked()
    fun onSearchItemClicked(itemId: String)
}

class SearchComponentImpl(componentContext: ComponentContext) : ComponentContext by componentContext,
    SearchComponent {
    override val query = MutableStateFlow("")

    override fun onQueryChanged(query: String) {
        this.query.value = query
    }

    override fun onSearchClicked() {
        println("mytest, search clicked with $query")
    }

    override fun onSearchItemClicked(itemId: String) {
        println("mytest, search clicked with $query")
    }
}
