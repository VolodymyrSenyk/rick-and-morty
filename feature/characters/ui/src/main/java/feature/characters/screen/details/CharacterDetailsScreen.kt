package feature.characters.screen.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import core.ui.components.scaffold.CustomScaffold
import core.ui.components.topappbar.SimpleTopAppBar
import core.ui.preview.ThemePreviewParameterProvider
import core.ui.theme.RickAndMortyTheme
import core.ui.theme.ThemeMode
import core.ui.utils.NavEventHandler
import feature.characters.navigation.CharacterDetailsDestination
import feature.characters.navigation.toModel
import feature.characters.preview.CharactersPreviewMocks
import feature.characters.screen.details.components.CharacterDetailsScreenContent
import feature.characters.screen.details.mvi.CharacterDetailsIntent
import feature.characters.screen.details.mvi.CharacterDetailsNavEvent
import feature.characters.screen.details.mvi.CharacterDetailsViewState
import navigation.compose.router.Router

@Composable
internal fun CharacterDetailsScreen(
    viewModel: CharacterDetailsViewModel,
    router: Router,
    args: CharacterDetailsDestination,
) {
    CharacterDetailsNavEventHandler(viewModel = viewModel, router = router)

    LaunchedEffect(args) {
        viewModel.onIntent(CharacterDetailsIntent.OnViewStarted(character = args.character.toModel()))
    }

    val viewState by viewModel.uiState.collectAsStateWithLifecycle()
    CustomScaffold(
        topAppBar = {
            SimpleTopAppBar(
                titleText = args.character.name,
                onNavigateBackClicked = { viewModel.onIntent(CharacterDetailsIntent.OnBackButtonClicked) },
            )
        }
    ) {
        CharacterDetailsScreenContent(viewState = viewState)
    }
}

@Composable
private fun CharacterDetailsNavEventHandler(viewModel: CharacterDetailsViewModel, router: Router) {
    NavEventHandler(viewModel) { mviNavEvent ->
        when (mviNavEvent) {
            is CharacterDetailsNavEvent.NavigateBack -> router.back()
        }
    }
}

@Preview
@Composable
private fun CharacterDetailsScreenPreview(@PreviewParameter(provider = ThemePreviewParameterProvider::class) theme: ThemeMode) {
    RickAndMortyTheme(themeMode = theme) {
        CustomScaffold(
            topAppBar = {
                SimpleTopAppBar(
                    titleText = CharactersPreviewMocks.character.name,
                    onNavigateBackClicked = {},
                )
            }
        ) {
            CharacterDetailsScreenContent(
                viewState = CharacterDetailsViewState(character = CharactersPreviewMocks.character),
            )
        }
    }
}
