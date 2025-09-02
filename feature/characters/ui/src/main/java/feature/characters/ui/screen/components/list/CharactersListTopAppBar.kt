package feature.characters.ui.screen.components.list

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import core.ui.components.topappbar.SimpleTopAppBar
import core.ui.components.topappbar.menu.ThemeChoosingMenuItem
import core.ui.theme.RickAndMortyTheme
import domain.settings.model.ThemeMode
import feature.characters.ui.R
import core.ui.R as CoreR

@Composable
internal fun CharactersListTopAppBar(
    modifier: Modifier = Modifier,
    onThemeSelected: (ThemeMode) -> Unit,
    onSortClicked: () -> Unit,
    onSearchClicked: () -> Unit,
) {
    SimpleTopAppBar(
        titleText = stringResource(CoreR.string.app_name),
        modifier = modifier
    ) {
        Row {
            SearchMenuItem(onClicked = onSearchClicked)
            ThemeChoosingMenuItem(onThemeSelected = onThemeSelected)
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

@Composable
private fun SearchMenuItem(
    modifier: Modifier = Modifier,
    onClicked: () -> Unit,
) {
    IconButton(
        onClick = { onClicked() },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(R.string.menu_search)
        )
    }
}

@Preview
@Composable
private fun CharactersListTopAppBarPreview() {
    RickAndMortyTheme {
        CharactersListTopAppBar(
            onThemeSelected = {},
            onSortClicked = {},
            onSearchClicked = {},
        )
    }
}
