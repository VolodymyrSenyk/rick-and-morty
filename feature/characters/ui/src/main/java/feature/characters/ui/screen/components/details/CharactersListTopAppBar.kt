package feature.characters.ui.screen.components.details

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import core.ui.components.topappbar.SimpleTopAppBar
import core.ui.components.topappbar.SimpleTopAppBarTitle
import core.ui.components.topappbar.menu.ThemeChoosingMenuItem
import core.ui.theme.RickAndMortyTheme
import domain.settings.model.ThemeMode
import feature.characters.ui.screen.preview.CharactersPreviewMocks

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun CharacterDetailsTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    onNavigateBackClicked: () -> Unit,
    onThemeSelected: (ThemeMode) -> Unit,
) {
    SimpleTopAppBar(
        title = title,
        onNavigateBackClicked = onNavigateBackClicked,
        modifier = modifier
    ) {
        Row {
            ThemeChoosingMenuItem(onThemeSelected = onThemeSelected)
        }
    }
}

@Preview
@Composable
private fun CharacterDetailsTopAppBarPreview() {
    RickAndMortyTheme {
        CharacterDetailsTopAppBar(
            title = { SimpleTopAppBarTitle(titleText = CharactersPreviewMocks.characterDetails.name) },
            onNavigateBackClicked = {},
            onThemeSelected = {},
        )
    }
}
