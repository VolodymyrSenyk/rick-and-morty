package feature.characters.ui.screen.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import feature.characters.presentation.viewmodel.mvi.search.CharactersSearchViewState

internal class CharactersSearchPreviewParameterProvider : PreviewParameterProvider<CharactersSearchViewState> {

    override val values = sequenceOf(
        CharactersSearchViewState(
            isSearching = true,
            searchResults = emptyList(),
            searchQuery = "",
            isInvalidSearchQuery = true,
            isLoading = false,
        ),
        CharactersSearchViewState(
            isSearching = true,
            searchResults = emptyList(),
            searchQuery = "dwdwfwaas",
            isInvalidSearchQuery = false,
            isLoading = false,
        ),
        CharactersSearchViewState(
            isSearching = true,
            searchResults = CharactersPreviewMocks.charactersList.filter { it.name.contains("Rick") },
            searchQuery = "Rick",
            isInvalidSearchQuery = false,
            isLoading = false,
        ),
        CharactersSearchViewState(
            isSearching = true,
            searchResults = CharactersPreviewMocks.charactersList.filter { it.name.contains("Rick") },
            searchQuery = "Rick",
            isInvalidSearchQuery = false,
            isLoading = true,
        ),
    )
}
