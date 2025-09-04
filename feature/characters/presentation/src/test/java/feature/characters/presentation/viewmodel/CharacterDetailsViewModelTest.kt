package feature.characters.presentation.viewmodel

import domain.characters.CharactersRepository
import domain.characters.model.Character
import domain.characters.usecase.GetCharacterByIdUseCase
import feature.characters.presentation.model.CharacterDetailsUi
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsIntent
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsNavEvent
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsViewState
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
class CharacterDetailsViewModelTest : BaseCoroutinesTest() {

    private lateinit var viewModel: CharacterDetailsViewModel

    @MockK
    lateinit var charactersRepository: CharactersRepository

    @BeforeEach
    override fun setUp() {
        super.setUp()
        viewModel = CharacterDetailsViewModel(
            getCharacterByIdUseCase = GetCharacterByIdUseCase(charactersRepository),
        )
    }

    @Test
    fun `character details initial state`() = runTest {
        val expectedViewState = CharacterDetailsViewState(
            character = null,
            showEmptyState = false,
            isLoading = false,
        )
        coVerify(exactly = 0) { charactersRepository.getCharacterById(any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `search error while data set loading`() = runTest {
        val characterId = "1"
        val expectedViewState = CharacterDetailsViewState(
            character = null,
            showEmptyState = true,
            isLoading = false,
        )

        coEvery { charactersRepository.getCharacterById(characterId) } throws IllegalStateException("")

        viewModel.onIntent(CharacterDetailsIntent.OnViewStarted(characterId))

        coVerify(exactly = 1) { charactersRepository.getCharacterById(any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `character details opened`() = runTest {
        val characterId = "1"
        val character = Character(
            id = characterId,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = "Earth (C-137)",
            location = "Citadel of Ricks",
            imageUrl = "someUrl",
        )
        val characterUi = CharacterDetailsUi(
            id = characterId,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = "Earth (C-137)",
            location = "Citadel of Ricks",
            imageUrl = "someUrl",
        )
        val expectedViewState = CharacterDetailsViewState(
            character = characterUi,
            showEmptyState = false,
            isLoading = false,
        )

        coEvery { charactersRepository.getCharacterById(characterId) } returns character

        viewModel.onIntent(CharacterDetailsIntent.OnViewStarted(characterId))

        coVerify(exactly = 1) { charactersRepository.getCharacterById(any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `on back button clicked`() = runTest {
        viewModel.onIntent(CharacterDetailsIntent.OnBackButtonClicked)
        assertEquals(CharacterDetailsNavEvent.NavigateBack, viewModel.navEvent.value)
    }
}
