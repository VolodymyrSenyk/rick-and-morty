package feature.characters.ui.screen.components.details

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import core.ui.components.topappbar.SimpleTopAppBar
import core.ui.components.topappbar.menu.ThemeChoosingMenuItem
import core.ui.theme.RickAndMortyTheme
import domain.settings.model.ThemeMode
import feature.characters.ui.screen.preview.CharactersPreviewMocks

@Composable
internal fun CharacterDetailsTopAppBar(
    modifier: Modifier = Modifier,
    titleText: String,
    onNavigateBackClicked: () -> Unit,
    onThemeSelected: (ThemeMode) -> Unit,
) {
    SimpleTopAppBar(
        titleText = titleText,
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
            titleText = CharactersPreviewMocks.characterDetails.name,
            onNavigateBackClicked = {},
            onThemeSelected = {},
        )
    }
}
