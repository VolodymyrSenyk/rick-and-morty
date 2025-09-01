package domain.settings

import domain.settings.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun saveThemeMode(themeMode: ThemeMode)

    fun observeThemeMode(): Flow<ThemeMode>
}
