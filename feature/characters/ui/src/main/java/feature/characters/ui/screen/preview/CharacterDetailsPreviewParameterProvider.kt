package feature.characters.ui.screen.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import feature.characters.presentation.model.CharacterDetailsUi
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsViewState

internal class CharacterDetailsPreviewParameterProvider : PreviewParameterProvider<CharacterDetailsViewState> {

    override val values = sequenceOf(
        CharacterDetailsViewState(
            character = with(CharactersPreviewMocks.character) {
                CharacterDetailsUi(
                    id = id,
                    name = name,
                    imageUrl = imageUrl,
                    status = "",
                    species = "",
                    type = "",
                    gender = "",
                    origin = "",
                    location = "",
                )
            },
        ),
        CharacterDetailsViewState(
            character = CharactersPreviewMocks.characterDetails,
        ),
    )
}
