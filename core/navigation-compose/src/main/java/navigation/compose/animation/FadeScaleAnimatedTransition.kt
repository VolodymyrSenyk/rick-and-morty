package navigation.compose.animation

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut

class FadeScaleAnimatedTransition(
    durationMillis: Int = 300,
    sourceScreenScale: Float = 0.99f,
    targetScreenScale: Float = 0.6f,
    targetScreenAlpha: Float = 0f,
) : BaseAnimatedTransition {

    private val fadeAnimSpec = tween<Float>(
        durationMillis = durationMillis,
        easing = FastOutLinearInEasing,
    )

    private val scaleAnimSpec = tween<Float>(
        durationMillis = durationMillis,
        easing = LinearEasing,
    )

    override val sourceScreen: SourceScreen = SourceScreen(
        exitTransition = {
            scaleOut(
                animationSpec = tween(durationMillis),
                targetScale = sourceScreenScale,
            )
        },
        popEnterTransition = {
            scaleIn(
                animationSpec = tween(durationMillis),
                initialScale = sourceScreenScale,
            )
        },
    )

    override val targetScreen: TargetScreen = TargetScreen(
        enterTransition = {
            fadeIn(
                animationSpec = fadeAnimSpec,
                initialAlpha = targetScreenAlpha,
            ) + scaleIn(
                animationSpec = scaleAnimSpec,
                initialScale = targetScreenScale,
            )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = fadeAnimSpec,
                targetAlpha = targetScreenAlpha,
            ) + scaleOut(
                animationSpec = scaleAnimSpec,
                targetScale = targetScreenScale,
            )
        },
    )
}
