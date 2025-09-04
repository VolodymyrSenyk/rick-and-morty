package feature.characters.ui.screen.components.details

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import core.ui.animation.visibility.FadeAnimatedVisibility
import core.ui.components.scaffold.CustomScaffold
import core.ui.theme.RickAndMortyTheme
import domain.settings.model.ThemeMode
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsViewState
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
    }
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
