package feature.characters.presentation.viewmodel

import domain.characters.CharactersRepository
import domain.characters.model.Character
import domain.characters.usecase.GetCharacterByIdUseCase
import feature.characters.presentation.model.CharacterDetailsUi
import feature.characters.presentation.model.CharacterUi
import feature.characters.presentation.model.toCharacterDetailsUi
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
        )
        coVerify(exactly = 0) { charactersRepository.getCharacterById(any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `search error while data set loading`() = runTest {
        val characterId = "1"
        val expectedViewState = CharacterDetailsViewState(
            character = characterUi(characterId).toCharacterDetailsUi(),
        )

        coEvery { charactersRepository.getCharacterById(characterId) } throws IllegalStateException("")

        viewModel.onIntent(CharacterDetailsIntent.OnViewStarted(characterUi(characterId)))

        coVerify(exactly = 1) { charactersRepository.getCharacterById(any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `character details opened`() = runTest {
        val characterId = "1"
        val expectedViewState = CharacterDetailsViewState(
            character = characterDetailsUi(characterId),
        )

        coEvery { charactersRepository.getCharacterById(characterId) } returns character(characterId)

        viewModel.onIntent(CharacterDetailsIntent.OnViewStarted(characterUi(characterId)))

        coVerify(exactly = 1) { charactersRepository.getCharacterById(any()) }
        assertEquals(expectedViewState, viewModel.uiState.value)
    }

    @Test
    fun `on image clicked`() = runTest {
        val characterId = "1"
        val character = characterDetailsUi(characterId)
        val sharedTransitionKey = "someKey"

        coEvery { charactersRepository.getCharacterById(characterId) } returns character(characterId)

        viewModel.onIntent(CharacterDetailsIntent.OnViewStarted(characterUi(characterId)))
        viewModel.onIntent(CharacterDetailsIntent.OnImageClicked(sharedTransitionKey))

        val event = CharacterDetailsNavEvent.NavigateToImageViewer(
            sharedTransitionKey = sharedTransitionKey,
            imageUrl = character.imageUrl,
        )
        assertEquals(event, viewModel.navEvent.value)
    }

    @Test
    fun `on empty image clicked`() = runTest {
        val sharedTransitionKey = "someKey"

        viewModel.onIntent(CharacterDetailsIntent.OnImageClicked(sharedTransitionKey))

        coVerify(exactly = 0) { charactersRepository.getCharacterById(any()) }
        assertEquals(null, viewModel.navEvent.value)
    }

    @Test
    fun `on back button clicked`() = runTest {
        viewModel.onIntent(CharacterDetailsIntent.OnBackButtonClicked)
        assertEquals(CharacterDetailsNavEvent.NavigateBack, viewModel.navEvent.value)
    }

    private fun character(id: String) = Character(
        id = id,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        type = "",
        gender = "Male",
        origin = "Earth (C-137)",
        location = "Citadel of Ricks",
        imageUrl = "someUrl",
    )

    private fun characterUi(id: String) = CharacterUi(
        id = id,
        uiId = "a_$id",
        name = "Rick Sanchez",
        imageUrl = "someUrl",
    )

    private fun characterDetailsUi(id: String) = CharacterDetailsUi(
        id = id,
        uiId = "a_$id",
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        type = "",
        gender = "Male",
        origin = "Earth (C-137)",
        location = "Citadel of Ricks",
        imageUrl = "someUrl",
    )
}
