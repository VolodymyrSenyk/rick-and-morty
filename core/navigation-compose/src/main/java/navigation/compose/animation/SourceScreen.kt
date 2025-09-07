package navigation.compose.animation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavBackStackEntry

data class SourceScreen(
    val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?,
    val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?,
)
