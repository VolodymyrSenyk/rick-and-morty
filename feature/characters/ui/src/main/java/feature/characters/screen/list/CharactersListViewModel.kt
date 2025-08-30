package feature.characters.screen.list

import arch.android.BaseSimpleMviViewModel
import arch.util.PaginationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.characters.model.CharacterDto
import domain.characters.usecase.GetCharactersUseCase
import feature.characters.model.AlphaSortType
import feature.characters.model.CharacterUi
import feature.characters.model.CharacterUiMapper
import feature.characters.screen.list.mvi.CharactersListIntent
import feature.characters.screen.list.mvi.CharactersListNavEvent
import feature.characters.screen.list.mvi.CharactersListSideEffect
import feature.characters.screen.list.mvi.CharactersListViewState
import javax.inject.Inject

@HiltViewModel
internal class CharactersListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val characterUiMapper: CharacterUiMapper,
) : BaseSimpleMviViewModel<CharactersListViewState, CharactersListIntent, CharactersListSideEffect, CharactersListNavEvent>(
    initialState = CharactersListViewState(),
) {

    private val paginationHelper = PaginationHelper()

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
        paginationHelper.resetPagination()
        val characters = getCharactersUseCase(page = paginationHelper.getPageForNewDataSet())
        setData(data = characters)
        sendSideEffect(CharactersListSideEffect.ScrollToTop)
    }

    private suspend fun onCharacterClicked(character: CharacterUi) {
        sendNavEvent(CharactersListNavEvent.NavigateToCharacterDetails(character = character))
    }

    private suspend fun onSortClicked() {
        val characters = currentState.charactersList.toMutableList().filterIsInstance<CharacterUi>()

        updateUiState { oldState ->
            oldState.copy(showProgress = true)
        }

        val sortedCharacters = when (sortType) {
            AlphaSortType.NOT_SORTED, AlphaSortType.DESCENDING -> {
                sortType = AlphaSortType.ASCENDING
                characters.sortedBy { it.name }
            }

            AlphaSortType.ASCENDING -> {
                sortType = AlphaSortType.DESCENDING
                characters.sortedByDescending { it.name }
            }
        }

        updateUiState { oldState ->
            oldState.copy(
                charactersList = sortedCharacters,
                showProgress = false,
            )
        }
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
        val characters = getCharactersUseCase(page = paginationHelper.getPageForNewDataSet())
        setData(data = characters)
    }

    private fun setData(data: List<CharacterDto>) {
        val dataList = if (paginationHelper.isCurrentDataSetEmpty()) {
            mutableListOf()
        } else {
            val currentDataSet = currentState.charactersList
            currentDataSet.toMutableList().run { filter { it is CharacterUi } }.toMutableList()
        }

        if (data.isNotEmpty()) {
            dataList.addAll(data.map { characterUiMapper(it) })
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
