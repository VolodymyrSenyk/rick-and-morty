package core.ui.animations.visibility

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.ui.theme.RickAndMortyTheme

@Composable
fun ScaleAnimatedVisibility(
    modifier: Modifier = Modifier,
    visible: Boolean,
    durationMillis: Int = 1_000,
    scale: Float = 0f,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleEnterTransition(
            durationMillis = durationMillis,
            scale = scale,
        ),
        exit = scaleExitTransition(
            durationMillis = durationMillis,
            scale = scale,
        ),
        content = content,
        modifier = modifier
    )
}

private fun scaleEnterTransition(
    durationMillis: Int,
    scale: Float,
): EnterTransition =
    scaleIn(
        animationSpec = tween(durationMillis),
        initialScale = scale,
    )

private fun scaleExitTransition(
    durationMillis: Int,
    scale: Float,
): ExitTransition =
    scaleOut(
        animationSpec = tween(durationMillis),
        targetScale = scale,
    )

@Preview
@Composable
private fun ScaleAnimatedVisibilityPreview() {
    RickAndMortyTheme {
        ScaleAnimatedVisibility(visible = true) {
            Icon(
                imageVector = Icons.Filled.AccountBox,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}
