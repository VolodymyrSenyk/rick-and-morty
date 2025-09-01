package feature.characters.ui.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import feature.characters.navigation.CharacterDetailsDestination
import feature.characters.navigation.CharactersListDestination
import feature.characters.presentation.viewmodel.CharacterDetailsViewModel
import feature.characters.presentation.viewmodel.CharactersListViewModel
import feature.characters.ui.screen.CharacterDetailsScreen
import feature.characters.ui.screen.CharactersListScreen
import feature.settings.viewmodel.SettingsViewModel
import feature.splash.viewmodel.SplashViewModel
import navigation.compose.router.JetpackRouter
import navigation.compose.utils.hiltActivityViewModel

fun NavGraphBuilder.charactersGraph(navController: NavController) {
    val router = JetpackRouter(navController)
    composable<CharactersListDestination>(
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
    ) { entry ->
        val viewModel = hiltViewModel<CharactersListViewModel>(entry)
        val settingsViewModel = hiltViewModel<SettingsViewModel>(entry)
        val splashViewModel = hiltActivityViewModel<SplashViewModel>()
        CharactersListScreen(
            viewModel = viewModel,
            splashViewModel = splashViewModel,
            settingsViewModel = settingsViewModel,
            router = router,
        )
    }
    composable<CharacterDetailsDestination>(
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
    ) { entry ->
        val args = entry.toRoute<CharacterDetailsDestination>()
        val viewModel = hiltViewModel<CharacterDetailsViewModel>(entry)
        val settingsViewModel = hiltViewModel<SettingsViewModel>(entry)
        CharacterDetailsScreen(viewModel = viewModel, settingsViewModel = settingsViewModel, router = router, args = args)
    }
}
