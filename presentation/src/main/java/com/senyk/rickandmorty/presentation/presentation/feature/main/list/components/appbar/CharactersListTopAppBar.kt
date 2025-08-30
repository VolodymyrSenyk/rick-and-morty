package com.senyk.rickandmorty.presentation.presentation.feature.main.list.components.appbar

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.senyk.rickandmorty.presentation.R
import core.ui.components.toolbar.SimpleTopAppBar
import core.ui.theme.RickAndMortyTheme
import core.ui.R as CoreR

@Composable
internal fun CharactersListTopAppBar(
    modifier: Modifier = Modifier,
    onSortClicked: () -> Unit,
) {
    SimpleTopAppBar(
        titleText = stringResource(CoreR.string.app_name),
        modifier = modifier
    ) {
        Row {
            SortMenuItem(onClicked = onSortClicked)
        }
    }
}

@Composable
private fun SortMenuItem(
    modifier: Modifier = Modifier,
    onClicked: () -> Unit,
) {
    IconButton(
        onClick = { onClicked() },
        modifier = modifier
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_sort_by_alpha),
            contentDescription = stringResource(R.string.menu_sort),
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}


@Preview
@Composable
private fun CharactersListTopAppBarPreview() {
    RickAndMortyTheme {
        CharactersListTopAppBar(
            onSortClicked = {},
        )
    }
}
