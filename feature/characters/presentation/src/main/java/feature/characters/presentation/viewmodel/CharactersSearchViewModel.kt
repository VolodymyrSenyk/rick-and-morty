package feature.characters.presentation.viewmodel

import arch.android.BaseSimpleMviViewModel
import arch.mvi.MviNavEvent
import arch.mvi.MviSideEffect
import arch.util.PaginationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.characters.usecase.GetCharactersByFilterUseCase
import feature.characters.presentation.model.toCharacterUi
import feature.characters.presentation.viewmodel.mvi.search.CharactersSearchIntent
import feature.characters.presentation.viewmodel.mvi.search.CharactersSearchViewState
import javax.inject.Inject

@HiltViewModel
class CharactersSearchViewModel @Inject constructor(
    private val getCharactersByFilterUseCase: GetCharactersByFilterUseCase,
    private val paginationHelper: PaginationHelper,
) : BaseSimpleMviViewModel<CharactersSearchViewState, CharactersSearchIntent, MviSideEffect, MviNavEvent>(
    initialState = CharactersSearchViewState.INITIAL,
) {

    override val tag: String = this.javaClass.simpleName

    override suspend fun executeIntent(mviIntent: CharactersSearchIntent) = when (mviIntent) {
        is CharactersSearchIntent.OnSearchToggle -> onSearchToggle()
        is CharactersSearchIntent.OnSearchQueryChanged -> onSearchQueryChanged(mviIntent.searchQuery)
        is CharactersSearchIntent.PerformSearch -> onPerformSearch()
        is CharactersSearchIntent.OnScrolled -> onScrolled(mviIntent.lastVisibleItemPosition)
    }

    private fun onSearchToggle() {
        updateUiState { oldState ->
            if (currentState.isSearching) {
                CharactersSearchViewState.INITIAL
            } else {
                oldState.copy(isSearching = true)
            }
        }
    }

    private fun onSearchQueryChanged(searchQuery: String) {
        updateUiState { oldState ->
            oldState.copy(
                searchQuery = searchQuery,
                isInvalidSearchQuery = searchQuery.isEmpty(),
                showBlockingProgress = true,
            )
        }
        onIntent(CharactersSearchIntent.PerformSearch(searchQuery))
    }

    private suspend fun onPerformSearch() {
        paginationHelper.resetPagination()
        loadCharacters()
    }

    private suspend fun onScrolled(lastVisibleItemPosition: Int) {
        val isNextDataSetNeeded = paginationHelper.isNextDataSetNeeded(lastVisibleItemPosition)
        val isLoading = currentState.showBlockingProgress
        if (isNextDataSetNeeded && !isLoading) {
            loadCharacters()
        }
    }

    override suspend fun onError(throwable: Throwable) {
        updateUiState { oldState ->
            oldState.copy(
                searchResults = emptyList(),
                showEmptyState = !currentState.isInvalidSearchQuery,
                showBlockingProgress = false,
                showPaginationProgress = false,
            )
        }
        super.onError(throwable)
    }

    private suspend fun loadCharacters() {
        val loadedDataSet = if (currentState.isInvalidSearchQuery) {
            emptyList()
        } else {
            getCharactersByFilterUseCase(
                page = paginationHelper.getPageForNewDataSet(),
                name = currentState.searchQuery,
            )
        }
        val dataList = if (paginationHelper.isCurrentDataSetEmpty()) {
            mutableListOf()
        } else {
            currentState.searchResults.toMutableList()
        }

        dataList.addAll(loadedDataSet.map { it.toCharacterUi(idPrefix = tag) })
        paginationHelper.onDataSetLoaded(loadedDataSet.size)

        updateUiState { oldState ->
            oldState.copy(
                searchResults = dataList,
                showEmptyState = dataList.isEmpty() && !currentState.isInvalidSearchQuery,
                showBlockingProgress = false,
                showPaginationProgress = paginationHelper.hasMoreData(),
            )
        }
    }
}
