package navigation.compose.utils

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import navigation.compose.NestedGraph

/**
 * Registers a nested navigation graph (`navigation {}`) within the current NavGraph.
 *
 * This function creates a scoped nested graph with its own `startDestination`, typically used
 * for grouping related destinations together (e.g., a feature module or screen flow).
 *
 * @param T The type of the nested graph, which implements [NestedGraph], providing the route and start destination.
 * @param navController The [NavController] used to navigate within the nested graph.
 * @param nestedGraph An instance of the nested graph that contains its route and start destination.
 * @param content Lambda that defines the destinations (composables) for this nested graph.
 */
inline fun <reified T : NestedGraph> NavGraphBuilder.registerNestedGraph(
    nestedGraph: T,
    navController: NavController,
    crossinline content: NavGraphBuilder.(NavController) -> Unit,
) {
    navigation<T>(startDestination = nestedGraph.startDestination) {
        content(navController)
    }
}
