package com.senyk.rickandmorty.presentation.presentation.feature.main.list.components.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.senyk.rickandmorty.presentation.R
import core.ui.theme.RickAndMortyTheme

@Composable
internal fun CharactersListEmptyState(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.message_characters_empty_list),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
            ),
        )
    }
}

@Preview
@Composable
private fun CharactersListEmptyStatePreview() {
    RickAndMortyTheme {
        CharactersListEmptyState()
    }
}
