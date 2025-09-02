package feature.characters.presentation.viewmodel

import domain.characters.CharacterRepository
import domain.characters.model.CharacterDto
import domain.characters.usecase.GetCharactersByFilterUseCase
import feature.characters.presentation.model.CharacterUi
import feature.characters.presentation.viewmodel.mvi.list.CharactersListIntent
import feature.characters.presentation.viewmodel.mvi.list.CharactersListNavEvent
import feature.characters.presentation.viewmodel.mvi.list.CharactersListSideEffect
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import testutil.BaseCoroutinesTest

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
class CharactersListViewModelTest : BaseCoroutinesTest() {

    private lateinit var viewModel: CharactersListViewModel

    @MockK
    lateinit var characterRepository: CharacterRepository

    @BeforeEach
    override fun setUp() {
        super.setUp()
        viewModel = CharactersListViewModel(
            getCharactersByFilterUseCase = GetCharactersByFilterUseCase(characterRepository),
        )
    }

    @Test
    fun `characters list initial state`() = runTest {
        coVerify(exactly = 0) { characterRepository.getCharactersByFilter(any(), any(), any(), any()) }
        with(viewModel.uiState.value) {
            assertEquals(emptyList<CharacterUi>(), this.charactersList)
            assertEquals(true, this.showProgress)
            assertEquals(false, this.isRefreshing)
            assertEquals(false, this.loadingNextDataSet)
        }
    }

    @Test
    fun `characters list opened`() = runTest {
        val testData = CharacterListTestData(startIndex = 0)

        coEvery { characterRepository.getCharactersByFilter(page = 1, null, null, null) } returns testData.charactersList

        viewModel.onIntent(CharactersListIntent.OnViewStarted)

        coVerify(exactly = 1) { characterRepository.getCharactersByFilter(any(), any(), any(), any()) }
        with(viewModel.uiState.value) {
            assertEquals(testData.charactersUiList, this.charactersList)
            assertEquals(false, this.showProgress)
            assertEquals(false, this.isRefreshing)
            assertEquals(true, this.loadingNextDataSet)
        }
    }

    @Test
    fun `characters list error while data set loading`() = runTest {
        coEvery { characterRepository.getCharactersByFilter(page = 1, null, null, null) } throws IllegalStateException("")

        viewModel.onIntent(CharactersListIntent.OnViewStarted)

        coVerify(exactly = 1) { characterRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(CharactersListSideEffect.ShowErrorMessage, viewModel.sideEffect.value)
        with(viewModel.uiState.value) {
            assertEquals(emptyList<CharacterUi>(), this.charactersList)
            assertEquals(false, this.showProgress)
            assertEquals(false, this.isRefreshing)
            assertEquals(false, this.loadingNextDataSet)
        }
    }

    @Test
    fun `characters list scrolled`() = runTest {
        val testDataFirstPage = CharacterListTestData(startIndex = 0)
        val testDataSecondPage = CharacterListTestData(startIndex = CharacterListTestData.PAGE_SIZE + 1)

        coEvery { characterRepository.getCharactersByFilter(page = 1, null, null, null) } returns testDataFirstPage.charactersList
        coEvery { characterRepository.getCharactersByFilter(page = 2, null, null, null) } returns testDataSecondPage.charactersList

        viewModel.onIntent(CharactersListIntent.OnViewStarted)
        viewModel.onIntent(CharactersListIntent.OnScrolled(lastVisibleItemPosition = CharacterListTestData.PAGE_SIZE))

        coVerify(exactly = 2) { characterRepository.getCharactersByFilter(any(), any(), any(), any()) }
        with(viewModel.uiState.value) {
            assertEquals(testDataFirstPage.charactersUiList + testDataSecondPage.charactersUiList, this.charactersList)
            assertEquals(false, this.showProgress)
            assertEquals(false, this.isRefreshing)
            assertEquals(true, this.loadingNextDataSet)
        }
    }

    @Test
    fun `characters list refreshed`() = runTest {
        val testDataFirstPage = CharacterListTestData(startIndex = 0)
        val testDataSecondPage = CharacterListTestData(startIndex = CharacterListTestData.PAGE_SIZE + 1)

        coEvery { characterRepository.getCharactersByFilter(page = 1, null, null, null) } returns testDataFirstPage.charactersList
        coEvery { characterRepository.getCharactersByFilter(page = 2, null, null, null) } returns testDataSecondPage.charactersList

        viewModel.onIntent(CharactersListIntent.OnViewStarted)
        viewModel.onIntent(CharactersListIntent.OnScrolled(lastVisibleItemPosition = CharacterListTestData.PAGE_SIZE))
        viewModel.onIntent(CharactersListIntent.OnRefreshed)

        coVerify(exactly = 3) { characterRepository.getCharactersByFilter(any(), any(), any(), any()) }
        with(viewModel.uiState.value) {
            assertEquals(testDataFirstPage.charactersUiList, this.charactersList)
            assertEquals(false, this.showProgress)
            assertEquals(false, this.isRefreshing)
            assertEquals(true, this.loadingNextDataSet)
        }
    }

    @Test
    fun `characters list sorted`() = runTest {
        val testData = CharacterListTestData(startIndex = 0)
        val charactersList = listOf(
            testData.characterDto.copy(id = "1", name = "C"),
            testData.characterDto.copy(id = "2", name = "A"),
            testData.characterDto.copy(id = "3", name = "D"),
            testData.characterDto.copy(id = "4", name = "B"),
        )
        val charactersUiList = listOf(
            testData.characterUi.copy(id = "1", name = "C"),
            testData.characterUi.copy(id = "2", name = "A"),
            testData.characterUi.copy(id = "3", name = "D"),
            testData.characterUi.copy(id = "4", name = "B"),
        )
        val charactersUiListSortedAscending = listOf(
            testData.characterUi.copy(id = "2", name = "A"),
            testData.characterUi.copy(id = "4", name = "B"),
            testData.characterUi.copy(id = "1", name = "C"),
            testData.characterUi.copy(id = "3", name = "D"),
        )
        val charactersUiListSortedDescending = listOf(
            testData.characterUi.copy(id = "3", name = "D"),
            testData.characterUi.copy(id = "1", name = "C"),
            testData.characterUi.copy(id = "4", name = "B"),
            testData.characterUi.copy(id = "2", name = "A"),
        )

        coEvery { characterRepository.getCharactersByFilter(page = 1, null, null, null) } returns charactersList

        viewModel.onIntent(CharactersListIntent.OnViewStarted)
        assertEquals(charactersUiList, viewModel.uiState.value.charactersList)

        viewModel.onIntent(CharactersListIntent.OnSortClicked)
        assertEquals(charactersUiListSortedAscending, viewModel.uiState.value.charactersList)

        viewModel.onIntent(CharactersListIntent.OnSortClicked)
        coVerify(exactly = 1) { characterRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(charactersUiListSortedDescending, viewModel.uiState.value.charactersList)
    }

    @Test
    fun `on character clicked`() = runTest {
        val testData = CharacterListTestData(startIndex = 0)
        val characterToNavigate = testData.charactersUiList[4]

        coEvery { characterRepository.getCharactersByFilter(page = 1, null, null, null) } returns testData.charactersList

        viewModel.onIntent(CharactersListIntent.OnViewStarted)
        viewModel.onIntent(CharactersListIntent.OnCharacterClicked(characterToNavigate))

        assertEquals(CharactersListNavEvent.NavigateToCharacterDetails(characterToNavigate), viewModel.navEvent.value)
    }

    @Test
    fun `on back button clicked`() = runTest {
        viewModel.onIntent(CharactersListIntent.OnBackButtonClicked)
        assertEquals(CharactersListNavEvent.NavigateBack, viewModel.navEvent.value)
    }

    class CharacterListTestData(val startIndex: Int) {

        val characterDto = CharacterDto(
            id = "1",
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = "Earth (C-137)",
            location = "Citadel of Ricks",
            imageUrl = "someUrl",
        )

        val characterUi = CharacterUi(
            id = "1",
            name = "Rick Sanchez",
            imageUrl = "someUrl",
        )

        val charactersList = List(PAGE_SIZE) { characterDto }.mapIndexed { index, model ->
            model.copy(id = (index + startIndex).toString())
        }

        val charactersUiList = List(PAGE_SIZE) { characterUi }.mapIndexed { index, model ->
            model.copy(id = (index + startIndex).toString())
        }

        companion object {
            const val PAGE_SIZE = 20
        }
    }
}
