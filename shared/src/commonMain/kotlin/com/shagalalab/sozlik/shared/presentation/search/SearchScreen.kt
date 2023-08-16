package com.shagalalab.sozlik.shared.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.shagalalab.sozlik.CommonRes
import com.shagalalab.sozlik.shared.domain.component.search.SearchComponent
import com.shagalalab.sozlik.shared.presentation.common.WordList
import com.shagalalab.sozlik.shared.util.parseHtml
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.compose.stringResource
import dev.icerock.moko.resources.format

@OptIn(ExperimentalMaterial3Api::class)
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
        if (state.isSuggested && state.suggestions.isNotEmpty()) {
            Text(
                CommonRes.strings.suggestion_found.format(state.query).localized().parseHtml(),
                modifier = Modifier.background(Color.LightGray.copy(alpha = 0.7f)).padding(16.dp).fillMaxWidth()
            )
        }
        if (state.suggestions.isEmpty() && state.query.isNotEmpty()) {
            Text(
                CommonRes.strings.suggestion_not_found.format(state.query).localized().parseHtml(),
                modifier = Modifier.background(Color.LightGray.copy(alpha = 0.7f)).padding(16.dp).fillMaxWidth()
            )
        } else {
            WordList(state.suggestions, modifier, component::onSearchItemClicked)
        }
    }
}
