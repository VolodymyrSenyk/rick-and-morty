package feature.characters.ui.screen.components.details

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import core.ui.components.scaffold.CustomScaffold
import core.ui.components.topappbar.SimpleTopAppBarTitle
import core.ui.theme.RickAndMortyTheme
import core.ui.utils.WithAnimatedVisibilityScope
import core.ui.utils.WithSharedTransitionScope
import domain.settings.model.ThemeMode
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsViewState
import feature.characters.ui.screen.preview.CharacterDetailsPreviewParameterProvider

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun CharacterDetailsScreenContent(
    viewState: CharacterDetailsViewState,
    onThemeSelected: (newThemeMode: ThemeMode) -> Unit,
    onBackButtonClicked: () -> Unit,
) {
    WithSharedTransitionScope {
        WithAnimatedVisibilityScope {
            val character = viewState.character
            CustomScaffold(
                background = MaterialTheme.colorScheme.surface,
                topAppBar = {
                    CharacterDetailsTopAppBar(
                        title = {
                            val titleText = character?.name ?: stringResource(core.ui.R.string.app_name)
                            SimpleTopAppBarTitle(
                                titleText = titleText,
                                modifier = Modifier
                                    .sharedElement(
                                        rememberSharedContentState(key = character?.uiId + titleText),
                                        animatedVisibilityScope = this,
                                    )
                            )
                        },
                        onNavigateBackClicked = onBackButtonClicked,
                        onThemeSelected = onThemeSelected,
                    )
                }
            ) {
                Box(
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = character?.uiId + viewState.character?.id),
                            animatedVisibilityScope = this@WithAnimatedVisibilityScope,
                        )
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    CharacterDetailsSection(character = viewState.character)
                }
            }
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
