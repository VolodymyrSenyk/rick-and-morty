package feature.characters.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import feature.characters.model.CharacterUi
import feature.characters.navigation.args.CharacterNavArg
import feature.characters.navigation.args.CharacterNavArgType
import feature.characters.screen.details.CharacterDetailsScreen
import feature.characters.screen.details.CharacterDetailsViewModel
import feature.characters.screen.list.CharactersListScreen
import feature.characters.screen.list.CharactersListViewModel
import navigation.compose.router.JetpackRouter
import kotlin.reflect.typeOf

fun NavGraphBuilder.charactersGraph(navController: NavController) {
    val router = JetpackRouter(navController)
    composable<CharactersListDestination>(
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
    ) { entry ->
        val viewModel = hiltViewModel<CharactersListViewModel>(entry)
        CharactersListScreen(viewModel = viewModel, router = router)
    }
    composable<CharacterDetailsDestination>(
        typeMap = mapOf(typeOf<CharacterNavArg>() to CharacterNavArgType),
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
    ) { entry ->
        val args = entry.toRoute<CharacterDetailsDestination>()
        val viewModel = hiltViewModel<CharacterDetailsViewModel>(entry)
        CharacterDetailsScreen(viewModel = viewModel, router = router, args = args)
    }
}

internal fun CharacterUi.toNavArg(): CharacterNavArg = CharacterNavArg(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin,
    location = location,
    imageUrl = imageUrl,
)

internal fun CharacterNavArg.toModel(): CharacterUi = CharacterUi(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin,
    location = location,
    imageUrl = imageUrl,
)
