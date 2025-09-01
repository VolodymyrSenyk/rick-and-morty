package feature.characters.screen.details

import feature.characters.model.CharacterUi
import feature.characters.screen.details.mvi.CharacterDetailsIntent
import feature.characters.screen.details.mvi.CharacterDetailsNavEvent
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

    @BeforeEach
    override fun setUp() {
        super.setUp()
        viewModel = CharacterDetailsViewModel()
    }

    @Test
    fun `character details opened`() = runTest {
        val avatarUrl = "someUrl"
        val character = CharacterUi(
            id = "1",
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = "Earth (C-137)",
            location = "Citadel of Ricks",
            imageUrl = avatarUrl,
        )

        viewModel.onIntent(CharacterDetailsIntent.OnViewStarted(character = character))

        assertEquals(character, viewModel.uiState.value.character)
    }

    @Test
    fun `on back button clicked`() = runTest {
        viewModel.onIntent(CharacterDetailsIntent.OnBackButtonClicked)
        assertEquals(CharacterDetailsNavEvent.NavigateBack, viewModel.navEvent.value)
    }
}
