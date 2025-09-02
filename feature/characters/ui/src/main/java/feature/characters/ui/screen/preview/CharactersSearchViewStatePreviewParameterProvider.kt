package feature.characters.ui.screen.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import feature.characters.presentation.viewmodel.mvi.search.CharactersSearchViewState

internal class CharactersSearchViewStatePreviewParameterProvider : PreviewParameterProvider<CharactersSearchViewState> {

    override val values = sequenceOf(
        CharactersSearchViewState(
            isSearching = true,
            searchResults = emptyList(),
            searchQuery = "",
            tooSmallSearchQuery = true,
            showProgress = false,
        ),
        CharactersSearchViewState(
            isSearching = true,
            searchResults = emptyList(),
            searchQuery = "dwdwfwaas",
            tooSmallSearchQuery = false,
            showProgress = false,
        ),
        CharactersSearchViewState(
            isSearching = true,
            searchResults = CharactersPreviewMocks.charactersList.filter { it.name.contains("Rick") },
            searchQuery = "Rick",
            tooSmallSearchQuery = false,
            showProgress = false,
        ),
        CharactersSearchViewState(
            isSearching = true,
            searchResults = CharactersPreviewMocks.charactersList.filter { it.name.contains("Rick") },
            searchQuery = "Rick",
            tooSmallSearchQuery = false,
            showProgress = true,
        ),
    )
}
