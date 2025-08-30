package com.senyk.rickandmorty.presentation.presentation.feature.main.mvi

import arch.mvi.MviIntent
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterUi

internal sealed class CharactersListIntent : MviIntent {

    data object OnViewStarted : CharactersListIntent()

    data class OnScrolled(val lastVisibleItemPosition: Int) : CharactersListIntent()

    data object OnRefreshed : CharactersListIntent()

    data class OnCharacterClicked(val character: CharacterUi) : CharactersListIntent()

    data object OnSortClicked : CharactersListIntent()

    data object OnBackButtonClicked : CharactersListIntent()
}
