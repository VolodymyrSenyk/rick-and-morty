package com.senyk.rickandmorty.presentation.presentation.feature.main.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.senyk.rickandmorty.presentation.presentation.feature.main.mvi.CharactersListViewState

internal class CharactersListViewStatePreviewParameterProvider : PreviewParameterProvider<CharactersListViewState> {

    override val values = sequenceOf(
        CharactersListViewState(
            charactersList = emptyList(),
            showProgress = true,
            isRefreshing = false,
        ),
        CharactersListViewState(
            charactersList = emptyList(),
            showProgress = false,
            isRefreshing = false,
        ),
        CharactersListViewState(
            charactersList = CharactersPreviewMocks.charactersList,
            showProgress = false,
            isRefreshing = false,
        ),
        CharactersListViewState(
            charactersList = CharactersPreviewMocks.charactersList,
            showProgress = false,
            isRefreshing = false,
            loadingNextDataSet = true,
        ),
    )
}
