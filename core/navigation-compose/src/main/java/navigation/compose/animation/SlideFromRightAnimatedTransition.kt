package navigation.compose.animation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

object SlideFromRightAnimatedTransition : BaseAnimatedTransition {

    override val sourceScreen: SourceScreen = SourceScreen(
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -it })
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it })
        },
    )

    override val targetScreen: TargetScreen = TargetScreen(
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it })
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { it })
        },
    )
}
