package feature.characters.ui.screen.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsViewState

internal class CharacterDetailsPreviewParameterProvider : PreviewParameterProvider<CharacterDetailsViewState> {

    override val values = sequenceOf(
        CharacterDetailsViewState(
            character = null,
            showEmptyState = false,
            isLoading = true,
        ),
        CharacterDetailsViewState(
            character = null,
            showEmptyState = true,
            isLoading = false,
        ),
        CharacterDetailsViewState(
            character = CharactersPreviewMocks.characterDetails,
            showEmptyState = false,
            isLoading = false,
        ),
    )
}
