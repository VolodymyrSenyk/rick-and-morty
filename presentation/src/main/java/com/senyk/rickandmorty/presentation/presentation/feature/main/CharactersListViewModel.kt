package com.senyk.rickandmorty.presentation.presentation.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.senyk.rickandmorty.domain.entity.CharacterDto
import com.senyk.rickandmorty.domain.usecase.orders.GetCharactersUseCase
import com.senyk.rickandmorty.presentation.R
import com.senyk.rickandmorty.presentation.presentation.base.BaseRxViewModel
import com.senyk.rickandmorty.presentation.presentation.entity.*
import com.senyk.rickandmorty.presentation.presentation.recycler.util.PaginationHelper
import com.senyk.rickandmorty.presentation.presentation.util.livedata.SingleEventLiveData
import com.senyk.rickandmorty.presentation.presentation.util.livedata.event.HandledEvent
import com.senyk.rickandmorty.presentation.presentation.util.livedata.event.navigation.NavigateToFragmentEvent
import com.senyk.rickandmorty.presentation.presentation.util.provider.ResourcesProvider
import javax.inject.Inject

class CharactersListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val characterUiMapper: CharacterUiMapper,
    resourcesProvider: ResourcesProvider
) : BaseRxViewModel() {

    private val directions = CharactersListFragmentDirections

    private val paginationHelper = PaginationHelper()

    private val progressListItem = ProgressListItem()
    private val emptyStateListItem = EmptyStateListItem(
        message = resourcesProvider.getString(R.string.message_characters_empty_list)
    )

    private val _charactersList: MutableLiveData<List<ListItem>> = MutableLiveData()
    val charactersList: LiveData<List<ListItem>>
        get() = _charactersList

    private val _scrollToTop = SingleEventLiveData<Boolean>()
    val scrollToTop: LiveData<Boolean>
        get() = _scrollToTop

    private var sortType = AlphaSortType.NOT_SORTED

    init {
        loadCharacters(withProgress = true)
    }

    fun onScroll(lastVisibleItemPosition: Int) {
        if (paginationHelper.isNextDataSetNeeded(lastVisibleItemPosition)) {
            loadCharacters(withProgress = false)
        }
    }

    fun onRefresh() {
        paginationHelper.resetPagination()
        subscribe(
            upstream = getCharactersUseCase(page = paginationHelper.getPageForNewDataSet()),
            onSuccess = { setData(it); _scrollToTop.setValue(true) })
    }

    fun onCharacterClick(character: CharacterUi) {
        val event = NavigateToFragmentEvent(
            directions.actionCharactersListFragmentToCharacterDetailsFragment(character = character)
        )
        _navigationEvent.value = HandledEvent(event)
    }

    fun onSortClick() {
        val characters = charactersList.value?.toMutableList() ?: return
        characters.remove(progressListItem)
        _charactersList.value = null
        _showProgress.setValue(true)
        _charactersList.value = when (sortType) {

            AlphaSortType.NOT_SORTED, AlphaSortType.DESCENDING -> {
                sortType = AlphaSortType.ASCENDING
                characters.sortedBy { (it as? CharacterUi)?.name }
            }

            AlphaSortType.ASCENDING -> {
                sortType = AlphaSortType.DESCENDING
                characters.sortedByDescending { (it as? CharacterUi)?.name }
            }
        }
        _showProgress.setValue(false)
    }

    private fun loadCharacters(withProgress: Boolean) {
        if (withProgress) {
            subscribeWithProgress(
                upstream = getCharactersUseCase(page = paginationHelper.getPageForNewDataSet()),
                onSuccess = { setData(it) }
            )
        } else {
            subscribe(
                upstream = getCharactersUseCase(page = paginationHelper.getPageForNewDataSet()),
                onSuccess = { setData(it) }
            )
        }
    }

    private fun setData(data: List<CharacterDto>) {
        val dataList = if (paginationHelper.isCurrentDataSetEmpty()) {
            mutableListOf()
        } else {
            val currentDataSet = _charactersList.value ?: listOf()
            currentDataSet.toMutableList().run { filter { it is CharacterUi } }.toMutableList()
        }

        if (data.isEmpty()) {
            dataList.add(emptyStateListItem)
        } else {
            dataList.addAll(data.map { characterUiMapper(it) })
        }

        paginationHelper.apply {
            onDataSetLoaded(data.size)
            if (hasMoreData()) dataList.add(progressListItem)
        }

        _charactersList.value = dataList
    }
}
