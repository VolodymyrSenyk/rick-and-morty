package com.senyk.rickandmorty.presentation.presentation.feature.main.mvi

import arch.mvi.ViewState
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterDetailsUi

internal data class CharacterDetailsViewState(
    val characterAvatarUrl: String? = null,
    val characterData: List<CharacterDetailsUi> = emptyList(),
) : ViewState
