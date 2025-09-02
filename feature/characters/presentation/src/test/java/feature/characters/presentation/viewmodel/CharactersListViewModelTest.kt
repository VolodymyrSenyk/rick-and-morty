package feature.characters.presentation.viewmodel

import domain.characters.CharactersRepository
import domain.characters.model.Character
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
        coVerify(exactly = 0) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        with(viewModel.uiState.value) {
            assertEquals(emptyList<CharacterUi>(), this.charactersList)
            assertEquals(true, this.isLoading)
            assertEquals(false, this.isRefreshing)
            assertEquals(false, this.isLoadingNextPage)
        }
    }

    @Test
    fun `characters list opened`() = runTest {
        val testData = CharacterListTestData(startIndex = 0)

        coEvery { charactersRepository.getCharactersByFilter(page = 1, null, null, null) } returns testData.charactersList

        viewModel.onIntent(CharactersListIntent.OnViewStarted)

        coVerify(exactly = 1) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        with(viewModel.uiState.value) {
            assertEquals(testData.charactersUiList, this.charactersList)
            assertEquals(false, this.isLoading)
            assertEquals(false, this.isRefreshing)
            assertEquals(true, this.isLoadingNextPage)
        }
    }

    @Test
    fun `characters list error while data set loading`() = runTest {
        coEvery { charactersRepository.getCharactersByFilter(page = 1, null, null, null) } throws IllegalStateException("")

        viewModel.onIntent(CharactersListIntent.OnViewStarted)

        coVerify(exactly = 1) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        assertEquals(CharactersListSideEffect.ShowErrorMessage, viewModel.sideEffect.value)
        with(viewModel.uiState.value) {
            assertEquals(emptyList<CharacterUi>(), this.charactersList)
            assertEquals(false, this.isLoading)
            assertEquals(false, this.isRefreshing)
            assertEquals(false, this.isLoadingNextPage)
        }
    }

    @Test
    fun `characters list scrolled`() = runTest {
        val testDataFirstPage = CharacterListTestData(startIndex = 0)
        val testDataSecondPage = CharacterListTestData(startIndex = CharacterListTestData.PAGE_SIZE + 1)

        coEvery { charactersRepository.getCharactersByFilter(page = 1, null, null, null) } returns testDataFirstPage.charactersList
        coEvery { charactersRepository.getCharactersByFilter(page = 2, null, null, null) } returns testDataSecondPage.charactersList

        viewModel.onIntent(CharactersListIntent.OnViewStarted)
        viewModel.onIntent(CharactersListIntent.OnScrolled(lastVisibleItemPosition = CharacterListTestData.PAGE_SIZE))

        coVerify(exactly = 2) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        with(viewModel.uiState.value) {
            assertEquals(testDataFirstPage.charactersUiList + testDataSecondPage.charactersUiList, this.charactersList)
            assertEquals(false, this.isLoading)
            assertEquals(false, this.isRefreshing)
            assertEquals(true, this.isLoadingNextPage)
        }
    }

    @Test
    fun `characters list refreshed`() = runTest {
        val testDataFirstPage = CharacterListTestData(startIndex = 0)
        val testDataSecondPage = CharacterListTestData(startIndex = CharacterListTestData.PAGE_SIZE + 1)

        coEvery { charactersRepository.getCharactersByFilter(page = 1, null, null, null) } returns testDataFirstPage.charactersList
        coEvery { charactersRepository.getCharactersByFilter(page = 2, null, null, null) } returns testDataSecondPage.charactersList

        viewModel.onIntent(CharactersListIntent.OnViewStarted)
        viewModel.onIntent(CharactersListIntent.OnScrolled(lastVisibleItemPosition = CharacterListTestData.PAGE_SIZE))
        viewModel.onIntent(CharactersListIntent.OnRefreshed)

        coVerify(exactly = 3) { charactersRepository.getCharactersByFilter(any(), any(), any(), any()) }
        with(viewModel.uiState.value) {
            assertEquals(testDataFirstPage.charactersUiList, this.charactersList)
            assertEquals(false, this.isLoading)
            assertEquals(false, this.isRefreshing)
            assertEquals(true, this.isLoadingNextPage)
        }
    }

    @Test
    fun `on character clicked`() = runTest {
        val testData = CharacterListTestData(startIndex = 0)
        val characterToNavigate = testData.charactersUiList[4]

        coEvery { charactersRepository.getCharactersByFilter(page = 1, null, null, null) } returns testData.charactersList

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

        val character = Character(
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

        val charactersList = List(PAGE_SIZE) { character }.mapIndexed { index, model ->
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
