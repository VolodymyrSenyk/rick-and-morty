package feature.splash.presentation.viewmodel

import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import testutil.BaseCoroutinesTest

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class SplashViewModelTest : BaseCoroutinesTest() {

    private lateinit var viewModel: SplashViewModel

    @BeforeEach
    override fun setUp() {
        super.setUp()
        viewModel = SplashViewModel()
    }

    @Test
    fun `splash should initially be visible`() = runTest {
        assertTrue(viewModel.show.value)
    }

    @Test
    fun `requirementDone removes requirement and keeps splash if others remain`() = runTest {
        viewModel.requirementDone(SplashViewModel.Requirement.THEME_LOADED)
        // Only one required requirement is done. Other is still active
        assertTrue(viewModel.show.value)
    }

    @Test
    fun `requirementDone hides splash when all required are done`() = runTest {
        viewModel.requirementDone(SplashViewModel.Requirement.THEME_LOADED)
        viewModel.requirementDone(SplashViewModel.Requirement.START_SCREEN_DATA_SET_LOADED)
        // All required requirements are done
        assertFalse(viewModel.show.value)
    }
}
