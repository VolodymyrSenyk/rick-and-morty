package core.ui.animation.visibility

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
fun FadeAnimatedVisibility(
    modifier: Modifier = Modifier,
    visible: Boolean,
    durationMillis: Int = 600,
    alpha: Float = 0f,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeEnterTransition(
            durationMillis = durationMillis / 2,
            alpha = alpha,
        ),
        exit = fadeExitTransition(
            durationMillis = durationMillis,
            alpha = alpha,
        ),
        content = content,
        modifier = modifier
    )
}

private fun fadeEnterTransition(
    durationMillis: Int,
    alpha: Float,
): EnterTransition =
    fadeIn(
        animationSpec = tween(durationMillis),
        initialAlpha = alpha,
    )

private fun fadeExitTransition(
    durationMillis: Int,
    alpha: Float,
): ExitTransition =
    fadeOut(
        animationSpec = tween(durationMillis),
        targetAlpha = alpha,
    )

@Preview
@Composable
private fun FadeAnimatedVisibilityPreview() {
    RickAndMortyTheme {
        FadeAnimatedVisibility(visible = true) {
            Icon(
                imageVector = Icons.Filled.AccountBox,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}
