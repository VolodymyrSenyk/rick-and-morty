package data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import domain.settings.SettingsRepository
import domain.settings.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsDataStoreRepository @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : SettingsRepository {

    override suspend fun saveThemeMode(themeMode: ThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[THEME_MODE] = themeMode.name
        }
    }

    override fun observeThemeMode(): Flow<ThemeMode> = context.dataStore.data.map { preferences ->
        val name = preferences[THEME_MODE]
        ThemeMode.entries.toTypedArray().firstOrNull { it.name == name } ?: ThemeMode.SYSTEM
    }

    companion object {
        private val THEME_MODE = stringPreferencesKey("theme_mode")
    }
}
