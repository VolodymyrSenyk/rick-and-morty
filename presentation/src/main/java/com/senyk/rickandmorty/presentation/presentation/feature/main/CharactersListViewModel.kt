package com.senyk.rickandmorty.presentation.presentation.feature.main

import arch.android.BaseSimpleMviViewModel
import com.senyk.rickandmorty.domain.entity.CharacterDto
import com.senyk.rickandmorty.domain.usecase.orders.GetCharactersUseCase
import com.senyk.rickandmorty.presentation.R
import com.senyk.rickandmorty.presentation.presentation.entity.AlphaSortType
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterUi
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterUiMapper
import com.senyk.rickandmorty.presentation.presentation.entity.EmptyStateListItem
import com.senyk.rickandmorty.presentation.presentation.entity.ProgressListItem
import com.senyk.rickandmorty.presentation.presentation.feature.main.mvi.CharactersListIntent
import com.senyk.rickandmorty.presentation.presentation.feature.main.mvi.CharactersListNavEvent
import com.senyk.rickandmorty.presentation.presentation.feature.main.mvi.CharactersListSideEffect
import com.senyk.rickandmorty.presentation.presentation.feature.main.mvi.CharactersListViewState
import com.senyk.rickandmorty.presentation.presentation.recycler.util.PaginationHelper
import com.senyk.rickandmorty.presentation.presentation.util.provider.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class CharactersListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val characterUiMapper: CharacterUiMapper,
    resourcesProvider: ResourcesProvider,
) : BaseSimpleMviViewModel<CharactersListViewState, CharactersListIntent, CharactersListSideEffect, CharactersListNavEvent>(
    initialState = CharactersListViewState(),
) {

    private val paginationHelper = PaginationHelper()

    private val progressListItem = ProgressListItem()
    private val emptyStateListItem = EmptyStateListItem(
        message = resourcesProvider.getString(R.string.message_characters_empty_list)
    )

    private var sortType = AlphaSortType.NOT_SORTED

    override val tag: String = this.javaClass.simpleName

    override suspend fun executeIntent(mviIntent: CharactersListIntent) = when (mviIntent) {
        is CharactersListIntent.OnViewStarted -> onViewStarted()
        is CharactersListIntent.OnScrolled -> onScrolled(lastVisibleItemPosition = mviIntent.lastVisibleItemPosition)
        is CharactersListIntent.OnRefreshed -> onRefreshed()
        is CharactersListIntent.OnCharacterClicked -> onCharacterClicked(character = mviIntent.character)
        is CharactersListIntent.OnSortClicked -> onSortClicked()
        is CharactersListIntent.OnBackButtonClicked -> onBackButtonClicked()
    }

    private suspend fun onViewStarted() {
        loadCharacters()
    }

    private suspend fun onScrolled(lastVisibleItemPosition: Int) {
        if (paginationHelper.isNextDataSetNeeded(lastVisibleItemPosition)) {
            loadCharacters()
        }
    }

    private suspend fun onRefreshed() {
        updateUiState { oldState ->
            oldState.copy(isRefreshing = true)
        }
        paginationHelper.resetPagination()
        val characters = getCharactersUseCase(page = paginationHelper.getPageForNewDataSet())
        setData(data = characters, scrollToTop = true)
    }

    private suspend fun onCharacterClicked(character: CharacterUi) {
        sendNavEvent(CharactersListNavEvent.NavigateToCharacterDetails(character = character))
    }

    private fun onSortClicked() {
        val characters = currentState.charactersList.toMutableList()
        characters.remove(progressListItem)

        updateUiState { oldState ->
            oldState.copy(showProgress = true)
        }

        val sortedCharacters = when (sortType) {

            AlphaSortType.NOT_SORTED, AlphaSortType.DESCENDING -> {
                sortType = AlphaSortType.ASCENDING
                characters.sortedBy { (it as? CharacterUi)?.name }
            }

            AlphaSortType.ASCENDING -> {
                sortType = AlphaSortType.DESCENDING
                characters.sortedByDescending { (it as? CharacterUi)?.name }
            }
        }

        updateUiState { oldState ->
            oldState.copy(
                charactersList = sortedCharacters,
                showProgress = false,
            )
        }
    }

    private suspend fun onBackButtonClicked() {
        sendNavEvent(CharactersListNavEvent.NavigateBack)
    }

    override suspend fun onError(throwable: Throwable) {
        updateUiState { oldState ->
            oldState.copy(
                isRefreshing = false,
                showProgress = false,
            )
        }
        sendSideEffect(CharactersListSideEffect.ShowErrorMessage)
        super.onError(throwable)
    }

    private suspend fun loadCharacters() {
        val characters = getCharactersUseCase(page = paginationHelper.getPageForNewDataSet())
        setData(data = characters)
    }

    private fun setData(data: List<CharacterDto>, scrollToTop: Boolean = false) {
        val dataList = if (paginationHelper.isCurrentDataSetEmpty()) {
            mutableListOf()
        } else {
            val currentDataSet = currentState.charactersList
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

        updateUiState { oldState ->
            oldState.copy(
                charactersList = dataList,
                isRefreshing = false,
                showProgress = false,
                scrollToTop = scrollToTop,
            )
        }
    }
}
