package feature.settings.viewmodel

import androidx.lifecycle.ViewModel
import arch.android.BaseCoroutinesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.settings.model.ThemeMode
import domain.settings.usecase.GetThemeModeUseCase
import domain.settings.usecase.SaveThemeModeUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * A [ViewModel] responsible for managing and persisting user settings,
 * such as the selected theme mode (e.g., light, dark, or system default).
 *
 * It exposes the current theme selection as a [StateFlow], which can be observed
 * by the UI to apply appropriate theming.
 *
 * Typically scoped to the Activity or application level using Hilt's `hiltViewModel()` mechanism.
 *
 * @constructor Injected via Hilt with required use cases for saving and retrieving theme mode.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val saveThemeModeUseCase: SaveThemeModeUseCase,
    getThemeModeUseCase: GetThemeModeUseCase,
) : BaseCoroutinesViewModel() {

    /**
     * A [StateFlow] that emits the current [ThemeMode] preference of the user.
     * Initialized eagerly and backed by [getThemeModeUseCase].
     *
     * The flow can emit `null` if the theme has not yet been set.
     */
    val themeModeFlow: StateFlow<ThemeMode?> = getThemeModeUseCase()
        .stateIn(coroutineScope, started = SharingStarted.Eagerly, initialValue = null)

    /**
     * Saves the selected [ThemeMode] preference using [saveThemeModeUseCase].
     *
     * @param themeMode The theme mode selected by the user (e.g., LIGHT, DARK, SYSTEM).
     */
    fun onThemeSelected(themeMode: ThemeMode) = launch {
        saveThemeModeUseCase(themeMode)
    }
}
