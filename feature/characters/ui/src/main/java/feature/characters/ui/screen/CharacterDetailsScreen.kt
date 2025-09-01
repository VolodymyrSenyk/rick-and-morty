package feature.characters.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import core.ui.R
import core.ui.components.scaffold.CustomScaffold
import core.ui.preview.ThemePreviewParameterProvider
import core.ui.theme.RickAndMortyTheme
import core.ui.utils.NavEventHandler
import domain.settings.model.ThemeMode
import feature.characters.navigation.CharacterDetailsDestination
import feature.characters.presentation.viewmodel.CharacterDetailsViewModel
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsIntent
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsNavEvent
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsViewState
import feature.characters.ui.screen.components.details.CharacterDetailsScreenContent
import feature.characters.ui.screen.components.details.CharacterDetailsTopAppBar
import feature.characters.ui.screen.preview.CharactersPreviewMocks
import feature.settings.viewmodel.SettingsViewModel
import navigation.compose.router.Router

@Composable
internal fun CharacterDetailsScreen(
    viewModel: CharacterDetailsViewModel,
    settingsViewModel: SettingsViewModel,
    router: Router,
    args: CharacterDetailsDestination,
) {
    CharacterDetailsNavEventHandler(viewModel = viewModel, router = router)

    LaunchedEffect(args) {
        viewModel.onIntent(CharacterDetailsIntent.OnViewStarted(characterId = args.characterId))
    }

    val viewState by viewModel.uiState.collectAsStateWithLifecycle()
    CustomScaffold(
        topAppBar = {
            CharacterDetailsTopAppBar(
                titleText = viewState.character?.name ?: stringResource(R.string.app_name),
                onNavigateBackClicked = { viewModel.onIntent(CharacterDetailsIntent.OnBackButtonClicked) },
                onThemeSelected = { newThemeMode -> settingsViewModel.onThemeSelected(newThemeMode) },
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
                CharacterDetailsTopAppBar(
                    titleText = CharactersPreviewMocks.characterDetails.name,
                    onNavigateBackClicked = {},
                    onThemeSelected = {},
                )
            }
        ) {
            CharacterDetailsScreenContent(
                viewState = CharacterDetailsViewState(character = CharactersPreviewMocks.characterDetails),
            )
        }
    }
}
