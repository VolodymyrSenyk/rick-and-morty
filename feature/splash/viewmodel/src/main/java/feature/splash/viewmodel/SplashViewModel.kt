package feature.settings.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * A [ViewModel] responsible for controlling the visibility of the splash screen
 * based on the completion of a set of required initialization steps.
 *
 * The splash screen remains visible until all required [Requirement]s are marked as done.
 * This ViewModel is typically used during app startup to coordinate multiple async tasks.
 *
 * @constructor Injected via Hilt.
 */
@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    /**
     * A mutable list of [Requirement]s that must be fulfilled before hiding the splash screen.
     */
    private var requirements: MutableList<Requirement> = Requirement.entries.toMutableList()

    /**
     * A [StateFlow] that represents whether the splash screen should be shown.
     * Emits `true` as long as any required [Requirement] remains unmet.
     */
    private val _showSplash = MutableStateFlow(true)
    val show: StateFlow<Boolean> = _showSplash.asStateFlow()

    /**
     * Marks the given [requirement] as completed.
     * Once all required [Requirement]s are completed, updates [show] to `false`.
     *
     * @param requirement The initialization requirement that has been fulfilled.
     */
    fun requirementDone(requirement: Requirement) {
        requirements.remove(requirement)
        _showSplash.tryEmit(requirements.any { it.isRequired })
    }

    /**
     * Represents an app startup requirement that determines whether the splash screen
     * should remain visible until it is fulfilled.
     *
     * @property isRequired Whether this requirement is mandatory to hide the splash screen.
     */
    enum class Requirement(val isRequired: Boolean) {
        /** Indicates that the theme mode has been loaded. */
        THEME_LOADED(isRequired = true),

        /**
         * Indicates that the data set required for the start screen has been loaded.
         *
         * This is typically used for start screens that display lists or collections of items,
         * where loading content before navigating to the main UI improves perceived performance
         * and prevents empty states or flickering.
         */
        START_SCREEN_DATA_SET_LOADED(isRequired = true),
    }
}
