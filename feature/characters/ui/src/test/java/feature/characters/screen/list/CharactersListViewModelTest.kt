package feature.characters.screen.list

import domain.characters.CharacterRepository
import domain.characters.model.CharacterDto
import domain.characters.model.GenderType
import domain.characters.model.StatusType
import domain.characters.usecase.GetCharactersUseCase
import feature.characters.model.CharacterUi
import feature.characters.model.CharacterUiMapper
import feature.characters.screen.list.mvi.CharactersListIntent
import feature.characters.screen.list.mvi.CharactersListNavEvent
import feature.characters.screen.list.mvi.CharactersListSideEffect
import feature.characters.util.provider.ResourcesProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
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

    @MockK
    lateinit var resourcesProvider: ResourcesProvider

    @BeforeEach
    override fun setUp() {
        super.setUp()
        viewModel = CharactersListViewModel(
            getCharactersUseCase = GetCharactersUseCase(characterRepository),
            characterUiMapper = CharacterUiMapper(resourcesProvider),
        )
    }

    @Test
    fun `characters list initial state`() = runTest {
        coVerify(exactly = 0) { characterRepository.getCharacters(any()) }
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

        coEvery { characterRepository.getCharacters(1) } returns testData.charactersList
        every { resourcesProvider.getString(any()) } returns CharacterListTestData.DATA_MOCK

        viewModel.onIntent(CharactersListIntent.OnViewStarted)

        coVerify(exactly = 1) { characterRepository.getCharacters(any()) }
        with(viewModel.uiState.value) {
            assertEquals(testData.charactersUiList, this.charactersList)
            assertEquals(false, this.showProgress)
            assertEquals(false, this.isRefreshing)
            assertEquals(true, this.loadingNextDataSet)
        }
    }

    @Test
    fun `characters list error while data set loading`() = runTest {
        coEvery { characterRepository.getCharacters(1) } throws IllegalStateException("")

        viewModel.onIntent(CharactersListIntent.OnViewStarted)

        coVerify(exactly = 1) { characterRepository.getCharacters(any()) }
        coVerify(exactly = 0) { resourcesProvider.getString(any()) }
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

        coEvery { characterRepository.getCharacters(1) } returns testDataFirstPage.charactersList
        coEvery { characterRepository.getCharacters(2) } returns testDataSecondPage.charactersList
        every { resourcesProvider.getString(any()) } returns CharacterListTestData.DATA_MOCK

        viewModel.onIntent(CharactersListIntent.OnViewStarted)
        viewModel.onIntent(CharactersListIntent.OnScrolled(lastVisibleItemPosition = CharacterListTestData.PAGE_SIZE))

        coVerify(exactly = 2) { characterRepository.getCharacters(any()) }
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

        coEvery { characterRepository.getCharacters(1) } returns testDataFirstPage.charactersList
        coEvery { characterRepository.getCharacters(2) } returns testDataSecondPage.charactersList
        every { resourcesProvider.getString(any()) } returns CharacterListTestData.DATA_MOCK

        viewModel.onIntent(CharactersListIntent.OnViewStarted)
        viewModel.onIntent(CharactersListIntent.OnScrolled(lastVisibleItemPosition = CharacterListTestData.PAGE_SIZE))
        viewModel.onIntent(CharactersListIntent.OnRefreshed)

        coVerify(exactly = 3) { characterRepository.getCharacters(any()) }
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
            testData.characterDto.copy(id = 1, name = "C"),
            testData.characterDto.copy(id = 2, name = "A"),
            testData.characterDto.copy(id = 3, name = "D"),
            testData.characterDto.copy(id = 4, name = "B"),
        )
        val charactersUiList = listOf(
            testData.characterUi.copy(id = 1, name = "C"),
            testData.characterUi.copy(id = 2, name = "A"),
            testData.characterUi.copy(id = 3, name = "D"),
            testData.characterUi.copy(id = 4, name = "B"),
        )
        val charactersUiListSortedAscending = listOf(
            testData.characterUi.copy(id = 2, name = "A"),
            testData.characterUi.copy(id = 4, name = "B"),
            testData.characterUi.copy(id = 1, name = "C"),
            testData.characterUi.copy(id = 3, name = "D"),
        )
        val charactersUiListSortedDescending = listOf(
            testData.characterUi.copy(id = 3, name = "D"),
            testData.characterUi.copy(id = 1, name = "C"),
            testData.characterUi.copy(id = 4, name = "B"),
            testData.characterUi.copy(id = 2, name = "A"),
        )

        coEvery { characterRepository.getCharacters(1) } returns charactersList
        every { resourcesProvider.getString(any()) } returns CharacterListTestData.DATA_MOCK

        viewModel.onIntent(CharactersListIntent.OnViewStarted)
        assertEquals(charactersUiList, viewModel.uiState.value.charactersList)

        viewModel.onIntent(CharactersListIntent.OnSortClicked)
        assertEquals(charactersUiListSortedAscending, viewModel.uiState.value.charactersList)

        viewModel.onIntent(CharactersListIntent.OnSortClicked)
        coVerify(exactly = 1) { characterRepository.getCharacters(any()) }
        assertEquals(charactersUiListSortedDescending, viewModel.uiState.value.charactersList)
    }

    @Test
    fun `on character clicked`() = runTest {
        val testData = CharacterListTestData(startIndex = 0)
        val characterToNavigate = testData.charactersUiList[4]

        coEvery { characterRepository.getCharacters(1) } returns testData.charactersList
        every { resourcesProvider.getString(any()) } returns CharacterListTestData.DATA_MOCK

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
            id = 1,
            name = "Rick Sanchez",
            status = StatusType.ALIVE,
            species = "Human",
            type = "",
            gender = GenderType.MALE,
            origin = "Earth (C-137)",
            location = "Citadel of Ricks",
            imageUrl = "someUrl",
        )

        val characterUi = CharacterUi(
            id = 1,
            name = "Rick Sanchez",
            status = DATA_MOCK,
            species = "Human",
            type = "",
            gender = DATA_MOCK,
            origin = "Earth (C-137)",
            location = "Citadel of Ricks",
            imageUrl = "someUrl",
        )

        val charactersList = List(PAGE_SIZE) { characterDto }.mapIndexed { index, model ->
            model.copy(id = index + startIndex)
        }

        val charactersUiList = List(PAGE_SIZE) { characterUi }.mapIndexed { index, model ->
            model.copy(id = index + startIndex)
        }

        companion object {
            const val DATA_MOCK = "Some data"
            const val PAGE_SIZE = 20
        }
    }
}
