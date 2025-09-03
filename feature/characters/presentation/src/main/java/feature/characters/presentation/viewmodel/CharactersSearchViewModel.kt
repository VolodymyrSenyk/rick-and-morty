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
) : BaseSimpleMviViewModel<CharactersSearchViewState, CharactersSearchIntent, MviSideEffect, MviNavEvent>(
    initialState = CharactersSearchViewState.INITIAL,
) {

    private val paginationHelper = PaginationHelper()

    override val tag: String = this.javaClass.simpleName

    override suspend fun executeIntent(mviIntent: CharactersSearchIntent) = when (mviIntent) {
        is CharactersSearchIntent.OnSearchToggle -> onSearchToggle()
        is CharactersSearchIntent.OnSearchQueryChanged -> onSearchQueryChanged(mviIntent.searchQuery)
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

    private suspend fun onSearchQueryChanged(searchQuery: String) {
        updateUiState { oldState ->
            oldState.copy(
                searchQuery = searchQuery,
                isInvalidSearchQuery = searchQuery.length < MIN_SEARCH_QUERY_SIZE,
                isLoading = true,
            )
        }
        paginationHelper.resetPagination()
        loadCharacters()
    }

    private suspend fun onScrolled(lastVisibleItemPosition: Int) {
        val isNextDataSetNeeded = paginationHelper.isNextDataSetNeeded(lastVisibleItemPosition)
        val isLoading = currentState.isLoading
        if (isNextDataSetNeeded && !isLoading) {
            loadCharacters()
        }
    }

    override suspend fun onError(throwable: Throwable) {
        updateUiState { oldState ->
            oldState.copy(
                isLoading = false,
                isLoadingNextPage = false,
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

        dataList.addAll(loadedDataSet.map { it.toCharacterUi() })
        paginationHelper.onDataSetLoaded(loadedDataSet.size)

        updateUiState { oldState ->
            oldState.copy(
                searchResults = dataList,
                isLoading = false,
                isLoadingNextPage = paginationHelper.hasMoreData(),
            )
        }
    }

    companion object {
        private const val MIN_SEARCH_QUERY_SIZE = 3
    }
}
