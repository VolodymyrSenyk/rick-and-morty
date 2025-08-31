package feature.characters.screen.details

import feature.characters.model.CharacterDetailsUi
import feature.characters.model.CharacterDetailsUiMapper
import feature.characters.model.CharacterUi
import feature.characters.screen.details.mvi.CharacterDetailsIntent
import feature.characters.screen.details.mvi.CharacterDetailsNavEvent
import feature.characters.util.provider.ResourcesProvider
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
class CharacterDetailsViewModelTest : BaseCoroutinesTest() {

    private lateinit var viewModel: CharacterDetailsViewModel

    @MockK
    lateinit var resourcesProvider: ResourcesProvider

    @BeforeEach
    override fun setUp() {
        super.setUp()
        viewModel = CharacterDetailsViewModel(
            characterDetailsUiMapper = CharacterDetailsUiMapper(resourcesProvider),
        )
    }

    @Test
    fun `character details opened`() = runTest {
        val labelMock = "Label"
        val avatarUrl = "someUrl"
        val character = CharacterUi(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = "Earth (C-137)",
            location = "Citadel of Ricks",
            imageUrl = avatarUrl,
        )
        val characterDetails = listOf(
            CharacterDetailsUi(label = labelMock, data = character.name),
            CharacterDetailsUi(label = labelMock, data = character.status),
            CharacterDetailsUi(label = labelMock, data = character.species),
            CharacterDetailsUi(label = labelMock, data = character.gender),
            CharacterDetailsUi(label = labelMock, data = character.origin),
            CharacterDetailsUi(label = labelMock, data = character.location),
        )

        every { resourcesProvider.getString(any()) } returns labelMock

        viewModel.onIntent(CharacterDetailsIntent.OnViewStarted(character = character))

        with(viewModel.uiState.value) {
            assertEquals(avatarUrl, this.characterAvatarUrl)
            assertEquals(characterDetails, this.characterData)
        }
    }

    @Test
    fun `on back button clicked`() = runTest {
        viewModel.onIntent(CharacterDetailsIntent.OnBackButtonClicked)
        assertEquals(CharacterDetailsNavEvent.NavigateBack, viewModel.navEvent.value)
    }
}
