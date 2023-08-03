package com.shagalalab.sozlik.shared.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.shagalalab.sozlik.CommonRes
import com.shagalalab.sozlik.shared.domain.component.search.SearchComponent
import com.shagalalab.sozlik.shared.presentation.common.WordList
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SearchScreen(component: SearchComponent, modifier: Modifier = Modifier) {
    val query by component.query.collectAsState()
    val state by component.state.collectAsState()

    Column {
        TopAppBar(title = { Text(stringResource(CommonRes.strings.app_name)) })
        OutlinedTextField(
            value = query,
            onValueChange = {
                component.onQueryChanged(it)
            },
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            placeholder = { Text(stringResource(CommonRes.strings.search_placeholder)) },
            leadingIcon = { Icon(Icons.Filled.Search, null) },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { component.onQueryChanged("") }) {
                        Icon(Icons.Filled.Close, null)
                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        )
        WordList(state.suggestions, modifier, component::onSearchItemClicked)
    }
}
