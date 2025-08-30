package com.senyk.rickandmorty.presentation.presentation.feature.main.mvi

import arch.mvi.MviNavEvent
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterUi

internal sealed class CharactersListNavEvent : MviNavEvent {

    data class NavigateToCharacterDetails(val character: CharacterUi) : CharactersListNavEvent()

    data object NavigateBack : CharactersListNavEvent()
}
