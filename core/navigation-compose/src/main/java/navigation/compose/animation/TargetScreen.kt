package navigation.compose.animation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavBackStackEntry

data class TargetScreen(
    val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?,
    val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?,
)
