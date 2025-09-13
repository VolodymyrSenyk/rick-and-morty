package app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import feature.characters.navigation.CharactersListDestination
import feature.characters.ui.navigation.charactersGraph
import feature.imageviewer.ui.navigation.imageViewerGraph

@Composable
fun RickAndMortyNavHost(rootNavController: NavHostController) {
    NavHost(navController = rootNavController, startDestination = CharactersListDestination) {
        charactersGraph(rootNavController)
        imageViewerGraph(rootNavController)
    }
}
