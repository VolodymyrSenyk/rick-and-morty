package com.senyk.rickandmorty.presentation.presentation.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.senyk.rickandmorty.domain.entity.CharacterDto
import com.senyk.rickandmorty.domain.usecase.orders.GetCharactersUseCase
import com.senyk.rickandmorty.presentation.R
import com.senyk.rickandmorty.presentation.presentation.base.BaseRxViewModel
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterUi
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterUiMapper
import com.senyk.rickandmorty.presentation.presentation.entity.EmptyStateListItem
import com.senyk.rickandmorty.presentation.presentation.entity.ListItem
import com.senyk.rickandmorty.presentation.presentation.recycler.util.PaginationHelper
import com.senyk.rickandmorty.presentation.presentation.util.livedata.event.HandledEvent
import com.senyk.rickandmorty.presentation.presentation.util.livedata.event.navigation.NavigateToFragmentEvent
import javax.inject.Inject

class CharactersListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val characterUiMapper: CharacterUiMapper
) : BaseRxViewModel() {

    private val directions = CharactersListFragmentDirections

    private val paginationHelper = PaginationHelper()

    private val _charactersList: MutableLiveData<List<ListItem>> = MutableLiveData()
    val charactersList: LiveData<List<ListItem>>
        get() = _charactersList

    init {
        loadCharacters(withProgress = true)
    }

    fun onRefresh() {
        loadCharacters(withProgress = false)
    }

    fun onCharacterClick(character: CharacterUi) {
        val event = NavigateToFragmentEvent(
            directions.actionCharactersListFragmentToCharacterDetailsFragment(character = character)
        )
        _navigationEvent.value = HandledEvent(event)
    }

    private fun loadCharacters(withProgress: Boolean) {
        if (withProgress) {
            subscribeWithProgress(
                upstream = getCharactersUseCase(page = 0),
                onSuccess = { setData(it) }
            )
        } else {
            subscribe(
                upstream = getCharactersUseCase(page = 0),
                onSuccess = { setData(it) }
            )
        }
    }

    private fun setData(characters: List<CharacterDto>) {
        _charactersList.value = if (characters.isNullOrEmpty()) {
            listOf(
                EmptyStateListItem(message = resourcesProvider.getString(R.string.message_characters_empty_list))
            )
        } else {
            characters.map { characterUiMapper(it) }
        }
    }
}
