package feature.imageviewer.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import core.ui.utils.ProvideAnimatedVisibilityScope
import feature.imageviewer.navigation.ImageViewerDestinations
import feature.imageviewer.ui.screen.ImageViewerScreen
import feature.settings.presentation.viewmodel.SettingsViewModel
import navigation.compose.router.JetpackRouter

fun NavGraphBuilder.imageViewerGraph(navController: NavController) {
    val router = JetpackRouter(navController)
    composable<ImageViewerDestinations> { entry ->
        val args = entry.toRoute<ImageViewerDestinations>()
        val settingsViewModel = hiltViewModel<SettingsViewModel>(entry)
        ProvideAnimatedVisibilityScope {
            ImageViewerScreen(
                sharedTransitionKey = args.sharedTransitionKey,
                url = args.imageUrl,
                settingsViewModel = settingsViewModel,
                router = router,
            )
        }
    }
}
