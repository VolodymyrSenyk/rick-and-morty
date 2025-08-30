package com.senyk.rickandmorty.presentation.presentation.feature.main.list.components.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import core.ui.theme.RickAndMortyTheme

@Composable
internal fun ProgressListItem(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
    }
}

@Preview
@Composable
private fun ProgressListItemPreview() {
    RickAndMortyTheme {
        ProgressListItem()
    }
}
