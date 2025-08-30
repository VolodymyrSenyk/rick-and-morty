package com.senyk.rickandmorty.presentation.presentation.feature.main.mvi

import arch.mvi.MviIntent
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterUi

internal sealed class CharacterDetailsIntent : MviIntent {

    data class OnViewStarted(val character: CharacterUi) : CharacterDetailsIntent()
}
