package feature.characters.ui.screen.components.details

import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import core.ui.animation.visibility.FadeAnimatedVisibility
import core.ui.animation.visibility.ScaleAnimatedVisibility
import core.ui.components.emptystate.SimpleEmptyState
import core.ui.components.progress.SimpleCircularProgress
import core.ui.components.scaffold.CustomScaffold
import core.ui.theme.RickAndMortyTheme
import core.ui.utils.widthWithCoef
import domain.settings.model.ThemeMode
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsViewState
import feature.characters.ui.R
import feature.characters.ui.screen.preview.CharacterDetailsPreviewParameterProvider

@Composable
internal fun CharacterDetailsScreenContent(
    viewState: CharacterDetailsViewState,
    onThemeSelected: (newThemeMode: ThemeMode) -> Unit,
    onBackButtonClicked: () -> Unit,
) {
    CustomScaffold(
        background = MaterialTheme.colorScheme.surface,
        topAppBar = {
            CharacterDetailsTopAppBar(
                titleText = viewState.character?.name ?: stringResource(core.ui.R.string.app_name),
                onNavigateBackClicked = onBackButtonClicked,
                onThemeSelected = onThemeSelected,
            )
        }
    ) {
        FadeAnimatedVisibility(visible = viewState.character != null) {
            CharacterDetailsSection(character = viewState.character)
        }
        ScaleAnimatedVisibility(
            visible = viewState.showEmptyState,
            durationMillis = 600,
            modifier = Modifier
                .width(widthWithCoef(0.8f))
                .align(Alignment.Center)
        ) {
            CharacterDetailsEmptyState()
        }
        FadeAnimatedVisibility(visible = viewState.isLoading) {
            SimpleCircularProgress(blocking = true)
        }
    }
}

@Composable
private fun CharacterDetailsEmptyState(modifier: Modifier = Modifier) {
    SimpleEmptyState(
        textMessage = stringResource(R.string.message_empty_state_character_details),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
        ),
        modifier = modifier
    )
}

@Preview
@Composable
private fun CharacterDetailsScreenContentPreview(
    @PreviewParameter(CharacterDetailsPreviewParameterProvider::class) viewState: CharacterDetailsViewState
) {
    RickAndMortyTheme {
        CharacterDetailsScreenContent(
            viewState = viewState,
            onThemeSelected = {},
            onBackButtonClicked = {},
        )
    }
}
