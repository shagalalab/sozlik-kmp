package com.shagalalab.sozlik.shared.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shagalalab.sozlik.shared.domain.component.search.SearchComponent

@Composable
fun SearchScreen(component: SearchComponent, modifier: Modifier = Modifier) {
    val query by component.query.collectAsState()

    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = component::onQueryChanged
        )
        Button(onClick = { component.onSearchClicked() }) {
            Text("Search")
        }
    }
}
