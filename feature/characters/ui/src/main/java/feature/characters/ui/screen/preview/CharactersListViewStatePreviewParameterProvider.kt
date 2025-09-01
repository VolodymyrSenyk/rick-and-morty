package feature.characters.ui.screen.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import feature.characters.presentation.viewmodel.mvi.list.CharactersListViewState

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
