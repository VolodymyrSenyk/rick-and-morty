package com.senyk.rickandmorty.presentation.presentation.feature.main.preview

import android.content.Context
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterDetailsUiMapper
import com.senyk.rickandmorty.presentation.presentation.feature.main.mvi.CharacterDetailsViewState
import com.senyk.rickandmorty.presentation.presentation.util.provider.ResourcesProvider

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
