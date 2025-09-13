package feature.imageviewer.ui.screen

import androidx.compose.runtime.Composable
import feature.imageviewer.ui.screen.components.ImageViewerScreenContent
import feature.settings.presentation.viewmodel.SettingsViewModel
import navigation.compose.router.Router

@Composable
internal fun ImageViewerScreen(
    sharedTransitionKey: String,
    url: String,
    settingsViewModel: SettingsViewModel,
    router: Router,
) {
    ImageViewerScreenContent(
        sharedTransitionKey = sharedTransitionKey,
        url = url,
        onNavigateBackClicked = router::back,
        onThemeSelected = { settingsViewModel.onThemeSelected(it) }
    )
}
