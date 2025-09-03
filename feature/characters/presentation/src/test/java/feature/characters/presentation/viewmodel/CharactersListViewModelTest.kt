package feature.characters.presentation.viewmodel

import domain.characters.CharactersRepository
import domain.characters.model.GenderType
import domain.characters.model.StatusType
import domain.characters.usecase.GetCharactersByFilterUseCase
import feature.characters.presentation.model.CharactersListFilter
import feature.characters.presentation.viewmodel.mvi.list.CharactersListIntent
import feature.characters.presentation.viewmodel.mvi.list.CharactersListNavEvent
import feature.characters.presentation.viewmodel.mvi.list.CharactersListSideEffect
import feature.characters.presentation.viewmodel.mvi.list.CharactersListViewState
import feature.characters.presentation.viewmodel.utlis.CharacterListTestData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertInstanceOf
import org.junit.jupiter.api.extension.ExtendWith
import testutil.BaseCoroutinesTest

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
class CharactersListViewModelTest : BaseCoroutinesTest() {

    private lateinit var viewModel: CharactersListViewModel

    @MockK
    lateinit var charactersRepository: CharactersRepository

    @BeforeEach
    override fun setUp() {
        super.setUp()
        viewModel = CharactersListViewModel(
            getCharactersByFilterUseCase = GetCharactersByFilterUseCase(charactersRepository),
        )
    }

    @Test
    fun `characters list initial state`() = runTest {
        val expectedViewState = CharactersListViewState(
            charactersList = emptyList(),
            isRefreshing = false,
            isLoading = true,
            isLoadingNextPage = false,
        )
        coVerify(exactly = 0) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `characters list opened`() = runTest {
        val testData = CharacterListTestData(startIndex = 0)
        val expectedViewState = CharactersListViewState(
            charactersList = testData.charactersUiList,
            isRefreshing = false,
            isLoading = false,
            isLoadingNextPage = true,
        )

        mockGetListRequest(page = 1, testData = testData)

        viewModel.onIntent(CharactersListIntent.OnViewStarted)

        coVerify(exactly = 1) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `characters list loaded empty data set`() = runTest {
        val testDataFirstPage = CharacterListTestData(startIndex = 0)
        val testDataSecondPage = CharacterListTestData(startIndex = testDataFirstPage.pageSize + 1, pageSize = 0)
        val expectedViewState = CharactersListViewState(
            charactersList = testDataFirstPage.charactersUiList,
            isRefreshing = false,
            isLoading = false,
            isLoadingNextPage = false,
        )

        mockGetListRequest(page = 1, testData = testDataFirstPage)
        mockGetListRequest(page = 2, testData = testDataSecondPage)

        viewModel.onIntent(CharactersListIntent.OnViewStarted)
        viewModel.onIntent(CharactersListIntent.OnScrolled(testDataFirstPage.charactersUiList.size))

        coVerify(exactly = 2) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `characters list error while data set loading`() = runTest {
        val expectedViewState = CharactersListViewState.INITIAL.copy(isLoading = false)

        mockGetListRequestWithError(page = 1)

        viewModel.onIntent(CharactersListIntent.OnViewStarted)

        coVerify(exactly = 1) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(CharactersListSideEffect.ShowErrorMessage, viewModel.sideEffect.value)
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `characters list scrolled`() = runTest {
        val testDataFirstPage = CharacterListTestData(startIndex = 0)
        val testDataSecondPage = CharacterListTestData(startIndex = testDataFirstPage.pageSize + 1)
        val expectedViewState = CharactersListViewState(
            charactersList = testDataFirstPage.charactersUiList + testDataSecondPage.charactersUiList,
            isRefreshing = false,
            isLoading = false,
            isLoadingNextPage = true,
        )

        mockGetListRequest(page = 1, testData = testDataFirstPage)
        mockGetListRequest(page = 2, testData = testDataSecondPage)

        viewModel.onIntent(CharactersListIntent.OnViewStarted)
        viewModel.onIntent(CharactersListIntent.OnScrolled(testDataFirstPage.charactersUiList.size))

        coVerify(exactly = 2) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `characters list scrolled to top`() = runTest {
        val testDataFirstPage = CharacterListTestData(startIndex = 0)
        val expectedViewState = CharactersListViewState(
            charactersList = testDataFirstPage.charactersUiList,
            isRefreshing = false,
            isLoading = false,
            isLoadingNextPage = true,
        )

        mockGetListRequest(page = 1, testData = testDataFirstPage)

        viewModel.onIntent(CharactersListIntent.OnViewStarted)
        viewModel.onIntent(CharactersListIntent.OnScrolled(0))

        coVerify(exactly = 1) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `characters list scrolled while already is loading`() = runTest {
        val expectedViewState = CharactersListViewState.INITIAL
        viewModel.onIntent(CharactersListIntent.OnScrolled(0))
        coVerify(exactly = 0) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `characters list refreshed`() = runTest {
        val testDataFirstPage = CharacterListTestData(startIndex = 0)
        val testDataSecondPage = CharacterListTestData(startIndex = testDataFirstPage.pageSize + 1)
        val expectedViewState = CharactersListViewState(
            charactersList = testDataFirstPage.charactersUiList,
            isRefreshing = false,
            isLoading = false,
            isLoadingNextPage = true,
        )

        mockGetListRequest(page = 1, testData = testDataFirstPage)
        mockGetListRequest(page = 2, testData = testDataSecondPage)

        viewModel.onIntent(CharactersListIntent.OnViewStarted)
        viewModel.onIntent(CharactersListIntent.OnScrolled(testDataFirstPage.charactersUiList.size))
        viewModel.onIntent(CharactersListIntent.OnRefreshed)

        coVerify(exactly = 3) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `characters list filter applied`() = runTest {
        val testData = CharacterListTestData(startIndex = 0)
        val filter = CharactersListFilter(statusType = StatusType.ALIVE)
        val expectedViewState = CharactersListViewState(
            charactersList = testData.charactersUiList,
            isRefreshing = false,
            isLoading = false,
            isLoadingNextPage = true,
        )

        mockGetListRequest(page = 1, testData = testData)
        mockGetListRequest(page = 1, statusType = StatusType.ALIVE, testData = testData)

        viewModel.onIntent(CharactersListIntent.OnViewStarted)
        viewModel.onIntent(CharactersListIntent.OnFilterClicked)

        assertInstanceOf<CharactersListNavEvent.NavigateToCharactersListFilter>(viewModel.navEvent.value)

        viewModel.onIntent(CharactersListIntent.OnFilterApplied(filter))

        coVerify(exactly = 2) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `on character clicked`() = runTest {
        val testData = CharacterListTestData(startIndex = 0)
        val characterToNavigate = testData.charactersUiList[4]

        mockGetListRequest(page = 1, testData = testData)

        viewModel.onIntent(CharactersListIntent.OnViewStarted)
        viewModel.onIntent(CharactersListIntent.OnCharacterClicked(characterToNavigate))

        assertEquals(CharactersListNavEvent.NavigateToCharacterDetails(characterToNavigate), viewModel.navEvent.value)
    }

    @Test
    fun `on back button clicked`() = runTest {
        viewModel.onIntent(CharactersListIntent.OnBackButtonClicked)
        assertEquals(CharactersListNavEvent.NavigateBack, viewModel.navEvent.value)
    }

    private fun mockGetListRequest(
        page: Int,
        statusType: StatusType? = null,
        genderType: GenderType? = null,
        testData: CharacterListTestData,
    ) {
        coEvery {
            charactersRepository.getCharactersByFilter(
                page = page,
                name = null,
                status = statusType,
                gender = genderType,
            )
        } returns testData.charactersList
    }

    private fun mockGetListRequestWithError(
        page: Int,
        statusType: StatusType? = null,
        genderType: GenderType? = null,
    ) {
        coEvery {
            charactersRepository.getCharactersByFilter(
                page = page,
                name = null,
                status = statusType,
                gender = genderType,
            )
        } throws IllegalStateException("")
    }
}
