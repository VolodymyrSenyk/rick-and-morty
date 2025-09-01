package domain.settings.usecase

import domain.settings.SettingsRepository
import domain.settings.model.ThemeMode
import javax.inject.Inject

class SaveThemeModeUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {

    suspend operator fun invoke(themeMode: ThemeMode) = settingsRepository.saveThemeMode(themeMode)
}
