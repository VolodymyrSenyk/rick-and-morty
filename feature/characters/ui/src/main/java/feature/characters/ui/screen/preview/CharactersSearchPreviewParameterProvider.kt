package feature.characters.ui.screen.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import feature.characters.presentation.viewmodel.mvi.search.CharactersSearchViewState

internal class CharactersSearchPreviewParameterProvider : PreviewParameterProvider<CharactersSearchViewState> {

    override val values = sequenceOf(
        CharactersSearchViewState(
            isSearching = true,
            searchQuery = "dwdwfwaas",
            isInvalidSearchQuery = false,
            searchResults = emptyList(),
            showEmptyState = true,
            showBlockingProgress = false,
            showPaginationProgress = false,
        ),
        CharactersSearchViewState(
            isSearching = true,
            searchQuery = "Rick",
            isInvalidSearchQuery = false,
            searchResults = CharactersPreviewMocks.charactersList.filter { it.name.contains("Rick") },
            showEmptyState = false,
            showBlockingProgress = false,
            showPaginationProgress = true,
        ),
        CharactersSearchViewState(
            isSearching = true,
            searchQuery = "Rick",
            isInvalidSearchQuery = false,
            searchResults = emptyList(),
            showEmptyState = false,
            showBlockingProgress = true,
            showPaginationProgress = false,
        ),
    )
}
