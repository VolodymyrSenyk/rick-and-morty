package feature.characters.ui.screen.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import feature.characters.presentation.viewmodel.mvi.list.CharactersListViewState

internal class CharactersListPreviewParameterProvider : PreviewParameterProvider<CharactersListViewState> {

    override val values = sequenceOf(
        CharactersListViewState(
            charactersList = emptyList(),
            isLoading = true,
            isRefreshing = false,
        ),
        CharactersListViewState(
            charactersList = emptyList(),
            isLoading = false,
            isRefreshing = false,
        ),
        CharactersListViewState(
            charactersList = CharactersPreviewMocks.charactersList,
            isLoading = false,
            isRefreshing = false,
        ),
        CharactersListViewState(
            charactersList = CharactersPreviewMocks.charactersList,
            isLoading = false,
            isRefreshing = false,
            isLoadingNextPage = true,
        ),
    )
}
