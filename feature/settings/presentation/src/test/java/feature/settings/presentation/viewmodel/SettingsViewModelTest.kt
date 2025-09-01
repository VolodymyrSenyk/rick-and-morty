package feature.settings.presentation.viewmodel

import domain.settings.SettingsRepository
import domain.settings.model.ThemeMode
import domain.settings.usecase.GetThemeModeUseCase
import domain.settings.usecase.SaveThemeModeUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import testutil.BaseCoroutinesTest

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class SettingsViewModelTest : BaseCoroutinesTest() {

    private lateinit var viewModel: SettingsViewModel

    @MockK
    lateinit var settingsRepository: SettingsRepository

    @Test
    fun `on switch from night mode clicked`() = runTest {
        coEvery { settingsRepository.observeThemeMode() } returns flow { emit(ThemeMode.DARK) }
        coJustRun { settingsRepository.saveThemeMode(any()) }

        viewModel = SettingsViewModel(
            saveThemeModeUseCase = SaveThemeModeUseCase(settingsRepository),
            getThemeModeUseCase = GetThemeModeUseCase(settingsRepository),
        )

        coVerify(exactly = 1) { settingsRepository.observeThemeMode() }
        Assertions.assertEquals(ThemeMode.DARK, viewModel.themeModeFlow.value)

        viewModel.onThemeSelected(themeMode = ThemeMode.LIGHT)

        coVerify(exactly = 1) { settingsRepository.observeThemeMode() }
        coVerify(exactly = 1) { settingsRepository.saveThemeMode(ThemeMode.LIGHT) }
        coVerify(inverse = true, atLeast = 1) { settingsRepository.saveThemeMode(ThemeMode.DARK) }
    }

    @Test
    fun `on switch from day mode clicked`() = runTest {
        coEvery { settingsRepository.observeThemeMode() } returns flow { emit(ThemeMode.LIGHT) }
        coJustRun { settingsRepository.saveThemeMode(any()) }

        viewModel = SettingsViewModel(
            saveThemeModeUseCase = SaveThemeModeUseCase(settingsRepository),
            getThemeModeUseCase = GetThemeModeUseCase(settingsRepository),
        )

        coVerify(exactly = 1) { settingsRepository.observeThemeMode() }
        Assertions.assertEquals(ThemeMode.LIGHT, viewModel.themeModeFlow.value)

        viewModel.onThemeSelected(themeMode = ThemeMode.DARK)

        coVerify(exactly = 1) { settingsRepository.observeThemeMode() }
        coVerify(exactly = 1) { settingsRepository.saveThemeMode(ThemeMode.DARK) }
        coVerify(inverse = true, atLeast = 1) { settingsRepository.saveThemeMode(ThemeMode.LIGHT) }
    }
}
