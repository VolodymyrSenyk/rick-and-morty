package feature.characters.presentation.viewmodel

import domain.characters.CharactersRepository
import domain.characters.usecase.GetCharactersByFilterUseCase
import feature.characters.presentation.viewmodel.mvi.search.CharactersSearchIntent
import feature.characters.presentation.viewmodel.mvi.search.CharactersSearchViewState
import feature.characters.presentation.viewmodel.utlis.CharacterListTestData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import testutil.BaseCoroutinesTest

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
class CharactersSearchViewModelTest : BaseCoroutinesTest() {

    private lateinit var viewModel: CharactersSearchViewModel

    @MockK
    lateinit var charactersRepository: CharactersRepository

    @BeforeEach
    override fun setUp() {
        super.setUp()
        viewModel = CharactersSearchViewModel(
            getCharactersByFilterUseCase = GetCharactersByFilterUseCase(charactersRepository),
        )
    }

    @Test
    fun `search feature initial state`() = runTest {
        val expectedViewState = CharactersSearchViewState(
            isSearching = false,
            searchResults = emptyList(),
            searchQuery = "",
            isInvalidSearchQuery = true,
            isLoading = false,
            isLoadingNextPage = false,
        )
        coVerify(exactly = 0) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `search started`() = runTest {
        val expectedViewState = CharactersSearchViewState.INITIAL.copy(isSearching = true)

        viewModel.onIntent(CharactersSearchIntent.OnSearchToggle)

        coVerify(exactly = 0) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `search finished`() = runTest {
        val expectedViewState = CharactersSearchViewState.INITIAL

        viewModel.onIntent(CharactersSearchIntent.OnSearchToggle)
        viewModel.onIntent(CharactersSearchIntent.OnSearchToggle)

        coVerify(exactly = 0) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `search performed`() = runTest {
        val searchQuery = "Rick"
        val testData = CharacterListTestData(startIndex = 0)
        val expectedViewState = CharactersSearchViewState(
            isSearching = true,
            searchResults = testData.charactersUiList,
            searchQuery = searchQuery,
            isInvalidSearchQuery = false,
            isLoading = false,
            isLoadingNextPage = true,
        )

        mockGetListRequest(page = 1, name = searchQuery, testData = testData)

        viewModel.onIntent(CharactersSearchIntent.OnSearchToggle)
        viewModel.onIntent(CharactersSearchIntent.OnSearchQueryChanged(searchQuery))

        coVerify(exactly = 1) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `search performed with too small search query`() = runTest {
        val searchQuery = "Ri"
        val expectedViewState = CharactersSearchViewState(
            isSearching = true,
            searchResults = emptyList(),
            searchQuery = searchQuery,
            isInvalidSearchQuery = true,
            isLoading = false,
            isLoadingNextPage = false,
        )

        viewModel.onIntent(CharactersSearchIntent.OnSearchToggle)
        viewModel.onIntent(CharactersSearchIntent.OnSearchQueryChanged(searchQuery))

        coVerify(exactly = 0) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `search results loaded empty data set`() = runTest {
        val searchQuery = "Rick"
        val testDataFirstPage = CharacterListTestData(startIndex = 0)
        val testDataSecondPage = CharacterListTestData(startIndex = testDataFirstPage.pageSize + 1, pageSize = 0)
        val expectedViewState = CharactersSearchViewState(
            isSearching = true,
            searchResults = testDataFirstPage.charactersUiList,
            searchQuery = searchQuery,
            isInvalidSearchQuery = false,
            isLoading = false,
            isLoadingNextPage = false,
        )

        mockGetListRequest(page = 1, name = searchQuery, testData = testDataFirstPage)
        mockGetListRequest(page = 2, name = searchQuery, testData = testDataSecondPage)

        viewModel.onIntent(CharactersSearchIntent.OnSearchToggle)
        viewModel.onIntent(CharactersSearchIntent.OnSearchQueryChanged(searchQuery))
        viewModel.onIntent(CharactersSearchIntent.OnScrolled(testDataFirstPage.charactersUiList.size))

        coVerify(exactly = 2) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `search error while data set loading`() = runTest {
        val searchQuery = "Rick"
        val expectedViewState = CharactersSearchViewState(
            isSearching = true,
            searchResults = emptyList(),
            searchQuery = searchQuery,
            isInvalidSearchQuery = false,
            isLoading = false,
            isLoadingNextPage = false,
        )

        mockGetListRequestWithError(page = 1, name = searchQuery)

        viewModel.onIntent(CharactersSearchIntent.OnSearchToggle)
        viewModel.onIntent(CharactersSearchIntent.OnSearchQueryChanged(searchQuery))

        coVerify(exactly = 1) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `search results list scrolled`() = runTest {
        val searchQuery = "Rick"
        val testDataFirstPage = CharacterListTestData(startIndex = 0)
        val testDataSecondPage = CharacterListTestData(startIndex = testDataFirstPage.pageSize + 1)
        val expectedViewState = CharactersSearchViewState(
            isSearching = true,
            searchResults = testDataFirstPage.charactersUiList + testDataSecondPage.charactersUiList,
            searchQuery = searchQuery,
            isInvalidSearchQuery = false,
            isLoading = false,
            isLoadingNextPage = true,
        )

        mockGetListRequest(page = 1, name = searchQuery, testData = testDataFirstPage)
        mockGetListRequest(page = 2, name = searchQuery, testData = testDataSecondPage)

        viewModel.onIntent(CharactersSearchIntent.OnSearchToggle)
        viewModel.onIntent(CharactersSearchIntent.OnSearchQueryChanged(searchQuery))
        viewModel.onIntent(CharactersSearchIntent.OnScrolled(testDataFirstPage.charactersUiList.size))

        coVerify(exactly = 2) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `search results list scrolled while already is loading`() = runTest {
        val searchQuery = "Rick"
        val testDataFirstPage = CharacterListTestData(startIndex = 0)
        val testDataSecondPage = CharacterListTestData(startIndex = testDataFirstPage.pageSize + 1)
        val expectedViewState = CharactersSearchViewState(
            isSearching = true,
            searchResults = emptyList(),
            searchQuery = searchQuery,
            isInvalidSearchQuery = false,
            isLoading = true,
            isLoadingNextPage = false,
        )
        val expectedViewStateAfterDelay = expectedViewState.copy(
            searchResults = testDataFirstPage.charactersUiList + testDataSecondPage.charactersUiList,
            isLoading = false,
            isLoadingNextPage = true,
        )

        mockGetListRequestWithDelay(page = 1, name = searchQuery, testData = testDataFirstPage)
        mockGetListRequest(page = 2, name = searchQuery, testData = testDataSecondPage)

        viewModel.onIntent(CharactersSearchIntent.OnSearchToggle)
        viewModel.onIntent(CharactersSearchIntent.OnSearchQueryChanged(searchQuery))
        viewModel.onIntent(CharactersSearchIntent.OnScrolled(testDataFirstPage.charactersUiList.size))

        coVerify(exactly = 1) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)

        advanceUntilIdle()

        coVerify(exactly = 2) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(expectedViewStateAfterDelay, viewModel.uiState.value)
    }

    private fun mockGetListRequest(page: Int, name: String?, testData: CharacterListTestData) {
        coEvery {
            charactersRepository.getCharactersByFilter(
                page = page,
                name = name,
                status = null,
                gender = null,
            )
        } returns testData.charactersList
    }

    private fun mockGetListRequestWithDelay(page: Int, name: String?, testData: CharacterListTestData) {
        coEvery {
            charactersRepository.getCharactersByFilter(
                page = page,
                name = name,
                status = null,
                gender = null,
            )
        } coAnswers {
            delay(5_000)
            testData.charactersList
        }
    }

    private fun mockGetListRequestWithError(page: Int, name: String?) {
        coEvery {
            charactersRepository.getCharactersByFilter(
                page = page,
                name = name,
                status = null,
                gender = null,
            )
        } throws IllegalStateException("")
    }
}
