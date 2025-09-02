package feature.characters.presentation.viewmodel

import arch.android.BaseSimpleMviViewModel
import arch.util.PaginationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.characters.model.CharacterDto
import domain.characters.usecase.GetCharactersByFilterUseCase
import feature.characters.presentation.model.CharacterUi
import feature.characters.presentation.model.CharactersListFilterSettings
import feature.characters.presentation.model.toCharacterUi
import feature.characters.presentation.viewmodel.mvi.list.CharactersListIntent
import feature.characters.presentation.viewmodel.mvi.list.CharactersListNavEvent
import feature.characters.presentation.viewmodel.mvi.list.CharactersListSideEffect
import feature.characters.presentation.viewmodel.mvi.list.CharactersListViewState
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    private val getCharactersByFilterUseCase: GetCharactersByFilterUseCase,
) : BaseSimpleMviViewModel<CharactersListViewState, CharactersListIntent, CharactersListSideEffect, CharactersListNavEvent>(
    initialState = CharactersListViewState(),
) {

    private val paginationHelper = PaginationHelper()
    private var filterSettings: CharactersListFilterSettings = CharactersListFilterSettings.EMPTY

    override val tag: String = this.javaClass.simpleName

    override suspend fun executeIntent(mviIntent: CharactersListIntent) = when (mviIntent) {
        is CharactersListIntent.OnViewStarted -> onViewStarted()
        is CharactersListIntent.OnScrolled -> onScrolled(lastVisibleItemPosition = mviIntent.lastVisibleItemPosition)
        is CharactersListIntent.OnRefreshed -> onRefreshed()
        is CharactersListIntent.OnCharacterClicked -> onCharacterClicked(character = mviIntent.character)
        is CharactersListIntent.OnFilterClicked -> onFilterClicked()
        is CharactersListIntent.OnFilterApplied -> onFilterApplied(filterSettings = mviIntent.filterSettings)
        is CharactersListIntent.OnBackButtonClicked -> onBackButtonClicked()
    }

    private suspend fun onViewStarted() {
        loadCharacters()
    }

    private suspend fun onScrolled(lastVisibleItemPosition: Int) {
        val isNextDataSetNeeded = paginationHelper.isNextDataSetNeeded(lastVisibleItemPosition)
        val isLoading = currentState.isRefreshing || currentState.showProgress
        if (isNextDataSetNeeded && !isLoading) {
            loadCharacters()
        }
    }

    private suspend fun onRefreshed() {
        updateUiState { oldState ->
            oldState.copy(isRefreshing = true)
        }
        filterSettings = CharactersListFilterSettings.EMPTY
        paginationHelper.resetPagination()
        loadCharacters()
        sendSideEffect(CharactersListSideEffect.ScrollToTop)
    }

    private suspend fun onCharacterClicked(character: CharacterUi) {
        sendNavEvent(CharactersListNavEvent.NavigateToCharacterDetails(character = character))
    }

    private suspend fun onFilterClicked() {
        sendNavEvent(CharactersListNavEvent.NavigateToFilterSettings(filterSettings))
    }

    private suspend fun onFilterApplied(filterSettings: CharactersListFilterSettings) {
        this.filterSettings = filterSettings
        updateUiState { oldState ->
            oldState.copy(showProgress = true)
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
                isRefreshing = false,
                showProgress = false,
            )
        }
        sendSideEffect(CharactersListSideEffect.ShowErrorMessage)
        super.onError(throwable)
    }

    private suspend fun loadCharacters() {
        val characters = getCharactersByFilterUseCase(
            page = paginationHelper.getPageForNewDataSet(),
            status = filterSettings.statusType,
            gender = filterSettings.genderType,
        )
        setData(data = characters)
    }

    private fun setData(data: List<CharacterDto>) {
        val dataList = if (paginationHelper.isCurrentDataSetEmpty()) {
            mutableListOf()
        } else {
            currentState.charactersList.toMutableList()
        }

        if (data.isNotEmpty()) {
            dataList.addAll(data.map { it.toCharacterUi() })
            paginationHelper.onDataSetLoaded(data.size)
        }

        updateUiState { oldState ->
            oldState.copy(
                charactersList = dataList,
                isRefreshing = false,
                showProgress = false,
                loadingNextDataSet = paginationHelper.hasMoreData(),
            )
        }
    }
}
