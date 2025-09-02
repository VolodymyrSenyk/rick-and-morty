package feature.characters.ui.screen.components.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import core.ui.components.topappbar.SimpleTopAppBar
import core.ui.theme.RickAndMortyTheme
import feature.characters.ui.R

@Composable
internal fun CharactersSearchTopAppBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchToggle: (Boolean) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    SimpleTopAppBar(
        title = {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = {
                    Text(
                        text = stringResource(R.string.hint_search_field),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                        )
                    )
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary,
                ),
                colors = TextFieldDefaults.colors().copy(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.onSurface,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
            )

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        },
        onNavigateBackClicked = {
            onSearchToggle(false)
        },
        menu = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear"
                    )
                }
            }
        },
        modifier = modifier,
    )
}

@Preview
@Composable
private fun CharactersSearchTopAppBarPreview() {
    RickAndMortyTheme {
        CharactersSearchTopAppBar(
            searchQuery = "Rick San",
            onSearchQueryChange = {},
            onSearchToggle = {},
        )
    }
}

@Preview
@Composable
private fun CharactersSearchTopAppBarEmptyPreview() {
    RickAndMortyTheme {
        CharactersSearchTopAppBar(
            searchQuery = "",
            onSearchQueryChange = {},
            onSearchToggle = {},
        )
    }
}
