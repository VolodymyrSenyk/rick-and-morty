package feature.characters.ui.screen.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import feature.characters.presentation.viewmodel.mvi.list.CharactersListViewState

internal class CharactersListPreviewParameterProvider : PreviewParameterProvider<CharactersListViewState> {

    override val values = sequenceOf(
        CharactersListViewState(
            charactersList = emptyList(),
            showEmptyState = false,
            showRefreshProgress = false,
            showBlockingProgress = true,
            showPaginationProgress = false,
        ),
        CharactersListViewState(
            charactersList = emptyList(),
            showEmptyState = true,
            showRefreshProgress = false,
            showBlockingProgress = false,
            showPaginationProgress = false,
        ),
        CharactersListViewState(
            charactersList = CharactersPreviewMocks.charactersList,
            showEmptyState = false,
            showRefreshProgress = false,
            showBlockingProgress = false,
            showPaginationProgress = true,
        ),
    )
}
