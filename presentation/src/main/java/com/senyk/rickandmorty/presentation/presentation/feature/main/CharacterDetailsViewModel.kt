package com.senyk.rickandmorty.presentation.presentation.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.senyk.rickandmorty.presentation.presentation.base.BaseViewModel
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterDetailsUi
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterDetailsUiMapper
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val characterDetailsUiMapper: CharacterDetailsUiMapper
) : BaseViewModel() {

    private val _characterDetails: MutableLiveData<List<CharacterDetailsUi>> = MutableLiveData()
    val characterDetails: LiveData<List<CharacterDetailsUi>>
        get() = _characterDetails

    fun setCharacter(character: CharacterUi) {
        _characterDetails.value = characterDetailsUiMapper(character)
    }
}
