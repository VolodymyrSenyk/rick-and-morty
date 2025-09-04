package feature.characters.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import feature.characters.navigation.CharacterDetailsDestination
import feature.characters.navigation.CharactersListDestination
import feature.characters.navigation.CharactersListFilterDestination
import feature.characters.navigation.args.CharacterNavArg
import feature.characters.navigation.args.CharacterNavArgType
import feature.characters.presentation.model.CharacterUi
import feature.characters.presentation.viewmodel.CharacterDetailsViewModel
import feature.characters.presentation.viewmodel.CharactersListViewModel
import feature.characters.presentation.viewmodel.CharactersSearchViewModel
import feature.characters.ui.screen.CharacterDetailsScreen
import feature.characters.ui.screen.CharactersListFilterSettingsDialog
import feature.characters.ui.screen.CharactersListScreen
import feature.settings.presentation.viewmodel.SettingsViewModel
import feature.splash.presentation.viewmodel.SplashViewModel
import navigation.compose.animation.FadeScaleAnimatedTransition
import navigation.compose.router.JetpackRouter
import navigation.compose.utils.hiltActivityViewModel
import kotlin.reflect.typeOf

fun NavGraphBuilder.charactersGraph(navController: NavController) {
    val router = JetpackRouter(navController)
    val listToDetailsTransition = FadeScaleAnimatedTransition()
    composable<CharactersListDestination>(
        exitTransition = listToDetailsTransition.sourceScreen.exitTransition,
        popEnterTransition = listToDetailsTransition.sourceScreen.popEnterTransition,
    ) { entry ->
        val viewModel = hiltViewModel<CharactersListViewModel>(entry)
        val searchViewModel = hiltViewModel<CharactersSearchViewModel>(entry)
        val settingsViewModel = hiltViewModel<SettingsViewModel>(entry)
        val splashViewModel = hiltActivityViewModel<SplashViewModel>()
        CharactersListScreen(
            viewModel = viewModel,
            searchViewModel = searchViewModel,
            splashViewModel = splashViewModel,
            settingsViewModel = settingsViewModel,
            router = router,
        )
    }
    composable<CharacterDetailsDestination>(
        typeMap = mapOf(typeOf<CharacterNavArg>() to CharacterNavArgType),
        enterTransition = listToDetailsTransition.targetScreen.enterTransition,
        popExitTransition = listToDetailsTransition.targetScreen.popExitTransition,
    ) { entry ->
        val args = entry.toRoute<CharacterDetailsDestination>()
        val viewModel = hiltViewModel<CharacterDetailsViewModel>(entry)
        val settingsViewModel = hiltViewModel<SettingsViewModel>(entry)
        CharacterDetailsScreen(
            character = args.character.toCharacterUi(),
            viewModel = viewModel,
            settingsViewModel = settingsViewModel,
            router = router,
        )
    }
    dialog<CharactersListFilterDestination> { entry ->
        val args = entry.toRoute<CharactersListFilterDestination>()
        CharactersListFilterSettingsDialog(
            router = router,
            previouslySelectedStatus = args.status,
            previouslySelectedGender = args.gender,
        )
    }
}

internal fun CharacterUi.toCharacterNavArg(): CharacterNavArg = CharacterNavArg(
    id = id,
    name = name,
    imageUrl = imageUrl,
)

internal fun CharacterNavArg.toCharacterUi(): CharacterUi = CharacterUi(
    id = id,
    name = name,
    imageUrl = imageUrl,
)
