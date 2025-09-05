package feature.characters.presentation.viewmodel

import arch.android.BaseSimpleMviViewModel
import arch.util.PaginationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.characters.usecase.GetCharactersByFilterUseCase
import feature.characters.presentation.model.CharacterUi
import feature.characters.presentation.model.CharactersListFilter
import feature.characters.presentation.model.toCharacterUi
import feature.characters.presentation.viewmodel.mvi.list.CharactersListIntent
import feature.characters.presentation.viewmodel.mvi.list.CharactersListNavEvent
import feature.characters.presentation.viewmodel.mvi.list.CharactersListSideEffect
import feature.characters.presentation.viewmodel.mvi.list.CharactersListViewState
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    private val getCharactersByFilterUseCase: GetCharactersByFilterUseCase,
    private val paginationHelper: PaginationHelper,
) : BaseSimpleMviViewModel<CharactersListViewState, CharactersListIntent, CharactersListSideEffect, CharactersListNavEvent>(
    initialState = CharactersListViewState.INITIAL,
) {

    private var charactersListFilter = CharactersListFilter()

    override val tag: String = this.javaClass.simpleName

    override suspend fun executeIntent(mviIntent: CharactersListIntent) = when (mviIntent) {
        is CharactersListIntent.OnViewStarted -> onViewStarted()
        is CharactersListIntent.OnScrolled -> onScrolled(mviIntent.lastVisibleItemPosition)
        is CharactersListIntent.OnRefreshed -> onRefreshed()
        is CharactersListIntent.OnCharacterClicked -> onCharacterClicked(mviIntent.character)
        is CharactersListIntent.OnFilterClicked -> onFilterClicked()
        is CharactersListIntent.OnFilterApplied -> onFilterApplied(mviIntent.charactersListFilter)
        is CharactersListIntent.OnBackButtonClicked -> onBackButtonClicked()
    }

    private suspend fun onViewStarted() {
        loadCharacters()
    }

    private suspend fun onScrolled(lastVisibleItemPosition: Int) {
        val isNextDataSetNeeded = paginationHelper.isNextDataSetNeeded(lastVisibleItemPosition)
        val isLoading = currentState.showRefreshProgress || currentState.showBlockingProgress
        if (isNextDataSetNeeded && !isLoading) {
            loadCharacters()
        }
    }

    private suspend fun onRefreshed() {
        updateUiState { oldState ->
            oldState.copy(showRefreshProgress = true)
        }
        charactersListFilter = CharactersListFilter()
        paginationHelper.resetPagination()
        loadCharacters()
        sendSideEffect(CharactersListSideEffect.ScrollToTop)
    }

    private suspend fun onCharacterClicked(character: CharacterUi) {
        sendNavEvent(CharactersListNavEvent.NavigateToCharacterDetails(character = character))
    }

    private suspend fun onFilterClicked() {
        sendNavEvent(CharactersListNavEvent.NavigateToCharactersListFilter(charactersListFilter))
    }

    private suspend fun onFilterApplied(filter: CharactersListFilter) {
        charactersListFilter = filter
        updateUiState { oldState ->
            oldState.copy(showBlockingProgress = true)
        }
        paginationHelper.resetPagination()
        loadCharacters()
        sendSideEffect(CharactersListSideEffect.ScrollToTop)
    }

    private suspend fun onBackButtonClicked() {
        sendNavEvent(CharactersListNavEvent.NavigateBack)
    }

    override suspend fun onError(throwable: Throwable) {
        updateUiState { oldState ->
            oldState.copy(
                showRefreshProgress = false,
                showBlockingProgress = false,
                showPaginationProgress = false,
            )
        }
        sendSideEffect(CharactersListSideEffect.ShowErrorMessage)
        super.onError(throwable)
    }

    private suspend fun loadCharacters() {
        val loadedDataList = getCharactersByFilterUseCase(
            page = paginationHelper.getPageForNewDataSet(),
            status = charactersListFilter.statusType,
            gender = charactersListFilter.genderType,
        )
        val resultDataList = if (paginationHelper.isCurrentDataSetEmpty()) {
            mutableListOf()
        } else {
            currentState.charactersList.toMutableList()
        }

        resultDataList.addAll(loadedDataList.map { it.toCharacterUi(tag) })
        paginationHelper.onDataSetLoaded(loadedDataList.size)

        updateUiState { oldState ->
            oldState.copy(
                charactersList = resultDataList,
                showEmptyState = resultDataList.isEmpty(),
                showRefreshProgress = false,
                showBlockingProgress = false,
                showPaginationProgress = paginationHelper.hasMoreData(),
            )
        }
    }
}
