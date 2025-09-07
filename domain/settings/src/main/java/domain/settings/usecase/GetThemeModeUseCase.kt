package domain.settings.usecase

import domain.settings.SettingsRepository
import domain.settings.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeModeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {

    operator fun invoke(): Flow<ThemeMode> = settingsRepository.observeThemeMode()
}
