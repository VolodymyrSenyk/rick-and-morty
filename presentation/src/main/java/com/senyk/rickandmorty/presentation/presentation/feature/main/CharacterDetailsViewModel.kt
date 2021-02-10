package com.senyk.rickandmorty.presentation.presentation.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.senyk.rickandmorty.presentation.presentation.base.BaseRxViewModel
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterUi
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor() : BaseRxViewModel() {

    private val _character: MutableLiveData<CharacterUi> = MutableLiveData()
    val character: LiveData<CharacterUi>
        get() = _character

    fun setCharacter(character: CharacterUi) {
        _character.value = character
    }
}
