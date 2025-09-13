package feature.characters.ui.screen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.senyk.rickandmorty.feature.characters.ui.R
import core.ui.utils.NavEventHandler
import core.ui.utils.SideEffectHandler
import feature.characters.presentation.model.CharacterUi
import feature.characters.presentation.viewmodel.CharacterDetailsViewModel
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsIntent
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsNavEvent
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsSideEffect
import feature.characters.ui.screen.components.details.CharacterDetailsScreenContent
import feature.imageviewer.navigation.ImageViewerDestinations
import feature.settings.presentation.viewmodel.SettingsViewModel
import navigation.compose.router.Router

@Composable
internal fun CharacterDetailsScreen(
    character: CharacterUi,
    viewModel: CharacterDetailsViewModel,
    settingsViewModel: SettingsViewModel,
    router: Router,
) {
    CharacterDetailsSideEffectHandler(viewModel = viewModel)

    CharacterDetailsNavEventHandler(viewModel = viewModel, router = router)

    LaunchedEffect(viewModel) {
        viewModel.onIntent(CharacterDetailsIntent.OnViewStarted(character))
    }

    val viewState by viewModel.uiState.collectAsStateWithLifecycle()
    viewState.character?.let {
        CharacterDetailsScreenContent(
            viewState = viewState,
            onThemeSelected = { settingsViewModel.onThemeSelected(it) },
            onBackButtonClicked = { viewModel.onIntent(CharacterDetailsIntent.OnBackButtonClicked) },
            onImageClicked = { viewModel.onIntent(CharacterDetailsIntent.OnImageClicked(it)) },
        )
    }
}

@Composable
private fun CharacterDetailsSideEffectHandler(viewModel: CharacterDetailsViewModel) {
    val context = LocalContext.current
    SideEffectHandler(viewModel) { mviEffect ->
        when (mviEffect) {
            is CharacterDetailsSideEffect.ShowErrorMessage -> {
                val message = context.getString(R.string.message_empty_state_character_details)
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Composable
private fun CharacterDetailsNavEventHandler(viewModel: CharacterDetailsViewModel, router: Router) {
    NavEventHandler(viewModel) { mviNavEvent ->
        when (mviNavEvent) {
            is CharacterDetailsNavEvent.NavigateToImageViewer -> {
                val destination = ImageViewerDestinations(
                    imageUrl = mviNavEvent.imageUrl,
                    sharedTransitionKey = mviNavEvent.sharedTransitionKey,
                )
                router.navigateTo(destination)
            }

            is CharacterDetailsNavEvent.NavigateBack -> router.back()
        }
    }
}
