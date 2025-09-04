package feature.characters.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import core.ui.utils.NavEventHandler
import feature.characters.presentation.viewmodel.CharacterDetailsViewModel
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsIntent
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsNavEvent
import feature.characters.ui.screen.components.details.CharacterDetailsScreenContent
import feature.settings.presentation.viewmodel.SettingsViewModel
import navigation.compose.router.Router

@Composable
internal fun CharacterDetailsScreen(
    characterId: String,
    viewModel: CharacterDetailsViewModel,
    settingsViewModel: SettingsViewModel,
    router: Router,
) {
    CharacterDetailsNavEventHandler(viewModel = viewModel, router = router)

    LaunchedEffect(viewModel) {
        viewModel.onIntent(CharacterDetailsIntent.OnViewStarted(characterId))
    }

    val viewState by viewModel.uiState.collectAsStateWithLifecycle()
    CharacterDetailsScreenContent(
        viewState = viewState,
        onThemeSelected = { settingsViewModel.onThemeSelected(it) },
        onBackButtonClicked = { viewModel.onIntent(CharacterDetailsIntent.OnBackButtonClicked) },
    )
}

@Composable
private fun CharacterDetailsNavEventHandler(viewModel: CharacterDetailsViewModel, router: Router) {
    NavEventHandler(viewModel) { mviNavEvent ->
        when (mviNavEvent) {
            is CharacterDetailsNavEvent.NavigateBack -> router.back()
        }
    }
}
