package com.senyk.rickandmorty.presentation.presentation.feature.main

import arch.android.BaseSimpleMviViewModel
import arch.mvi.MviNavEvent
import arch.mvi.MviSideEffect
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterDetailsUiMapper
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterUi
import com.senyk.rickandmorty.presentation.presentation.feature.main.mvi.CharacterDetailsIntent
import com.senyk.rickandmorty.presentation.presentation.feature.main.mvi.CharacterDetailsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class CharacterDetailsViewModel @Inject constructor(
    private val characterDetailsUiMapper: CharacterDetailsUiMapper
) : BaseSimpleMviViewModel<CharacterDetailsViewState, CharacterDetailsIntent, MviSideEffect, MviNavEvent>(
    initialState = CharacterDetailsViewState()
) {

    override val tag: String = this.javaClass.simpleName

    override suspend fun executeIntent(mviIntent: CharacterDetailsIntent) = when (mviIntent) {
        is CharacterDetailsIntent.OnViewStarted -> onViewStarted(character = mviIntent.character)
    }

    private fun onViewStarted(character: CharacterUi) {
        updateUiState { oldState ->
            oldState.copy(
                characterAvatarUrl = character.imageUrl,
                characterData = characterDetailsUiMapper(character),
            )
        }
    }
}
