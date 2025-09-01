package feature.characters.screen.details

import domain.characters.CharacterRepository
import domain.characters.model.CharacterDto
import domain.characters.usecase.GetCharacterByIdUseCase
import feature.characters.model.CharacterDetailsUi
import feature.characters.screen.details.mvi.CharacterDetailsIntent
import feature.characters.screen.details.mvi.CharacterDetailsNavEvent
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
    lateinit var characterRepository: CharacterRepository

    @BeforeEach
    override fun setUp() {
        super.setUp()
        viewModel = CharacterDetailsViewModel(
            getCharacterByIdUseCase = GetCharacterByIdUseCase(characterRepository),
        )
    }

    @Test
    fun `character details initial state`() = runTest {
        coVerify(exactly = 0) { characterRepository.getCharacterById(any()) }
        with(viewModel.uiState.value) {
            assertEquals(null, this.character)
            assertEquals(true, this.isLoading)
        }
    }

    @Test
    fun `character details opened`() = runTest {
        val id = "1"
        val characterDto = CharacterDto(
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
        val characterUi = CharacterDetailsUi(
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

        coEvery { characterRepository.getCharacterById(id) } returns characterDto

        viewModel.onIntent(CharacterDetailsIntent.OnViewStarted(characterId = id))

        coVerify(exactly = 1) { characterRepository.getCharacterById(any()) }
        assertEquals(characterUi, viewModel.uiState.value.character)
    }

    @Test
    fun `on back button clicked`() = runTest {
        viewModel.onIntent(CharacterDetailsIntent.OnBackButtonClicked)
        assertEquals(CharacterDetailsNavEvent.NavigateBack, viewModel.navEvent.value)
    }
}
