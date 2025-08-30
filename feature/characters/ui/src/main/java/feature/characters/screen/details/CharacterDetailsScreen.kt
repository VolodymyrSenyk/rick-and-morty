package feature.characters.screen.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import core.ui.components.scaffold.CustomScaffold
import core.ui.components.toolbar.SimpleTopAppBar
import core.ui.preview.ThemePreviewParameterProvider
import core.ui.theme.RickAndMortyTheme
import core.ui.theme.ThemeMode
import feature.characters.navigation.CharacterDetailsDestination
import feature.characters.navigation.toModel
import feature.characters.preview.CharacterDetailsViewStatePreviewMock
import feature.characters.preview.CharactersPreviewMocks
import feature.characters.screen.details.components.CharacterDetailsScreenContent
import feature.characters.screen.details.mvi.CharacterDetailsIntent

@Composable
internal fun CharacterDetailsScreen(
    viewModel: CharacterDetailsViewModel,
    args: CharacterDetailsDestination,
) {
    LaunchedEffect(args) {
        viewModel.onIntent(CharacterDetailsIntent.OnViewStarted(character = args.character.toModel()))
    }

    val viewState by viewModel.uiState.collectAsStateWithLifecycle()
    CustomScaffold(topAppBar = { SimpleTopAppBar(titleText = args.character.name) }) {
        CharacterDetailsScreenContent(viewState = viewState)
    }
}

@Preview
@Composable
private fun CharacterDetailsScreenPreview(@PreviewParameter(provider = ThemePreviewParameterProvider::class) theme: ThemeMode) {
    RickAndMortyTheme(themeMode = theme) {
        CustomScaffold(topAppBar = { SimpleTopAppBar(titleText = CharactersPreviewMocks.character.name) }) {
            val viewState = CharacterDetailsViewStatePreviewMock.provideCharacterDetailsViewState(LocalContext.current)
            CharacterDetailsScreenContent(viewState = viewState)
        }
    }
}
