package feature.characters.preview

import android.content.Context
import feature.characters.model.CharacterDetailsUiMapper
import feature.characters.screen.details.mvi.CharacterDetailsViewState
import feature.characters.util.provider.ResourcesProvider

internal object CharacterDetailsViewStatePreviewMock {

    fun provideCharacterDetailsViewState(context: Context): CharacterDetailsViewState {
        val resourcesProvider = ResourcesProvider(context)
        val mapper = CharacterDetailsUiMapper(resourcesProvider)
        val characterDetails = mapper(CharactersPreviewMocks.character)
        return CharacterDetailsViewState(
            characterAvatarUrl = CharactersPreviewMocks.avatarConstructor(),
            characterData = characterDetails,
        )
    }
}
