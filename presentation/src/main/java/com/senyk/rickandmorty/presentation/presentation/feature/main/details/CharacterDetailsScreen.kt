package com.senyk.rickandmorty.presentation.presentation.feature.main.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.senyk.rickandmorty.presentation.presentation.feature.main.CharacterDetailsFragmentArgs
import com.senyk.rickandmorty.presentation.presentation.feature.main.CharacterDetailsViewModel
import com.senyk.rickandmorty.presentation.presentation.feature.main.details.components.CharacterDetailsScreenContent
import com.senyk.rickandmorty.presentation.presentation.feature.main.mvi.CharacterDetailsIntent
import com.senyk.rickandmorty.presentation.presentation.feature.main.preview.CharacterDetailsViewStatePreviewMock
import com.senyk.rickandmorty.presentation.presentation.feature.main.preview.CharactersPreviewMocks
import core.ui.components.scaffold.CustomScaffold
import core.ui.components.toolbar.SimpleTopAppBar
import core.ui.preview.ThemePreviewParameterProvider
import core.ui.theme.RickAndMortyTheme
import core.ui.theme.ThemeMode

@Composable
internal fun CharacterDetailsScreen(
    viewModel: CharacterDetailsViewModel,
    args: CharacterDetailsFragmentArgs,
) {
    LaunchedEffect(args) {
        viewModel.onIntent(CharacterDetailsIntent.OnViewStarted(character = args.character))
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
