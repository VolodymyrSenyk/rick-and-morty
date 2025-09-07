package core.ui.animation.visibility

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
fun SlideTopToBottomAnimatedVisibility(
    modifier: Modifier = Modifier,
    visible: Boolean,
    durationMillis: Int = 1_000,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            animationSpec = tween(durationMillis),
            initialOffsetY = { -it },
        ),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis),
            targetOffsetY = { -it },
        ),
        content = content,
        modifier = modifier
    )
}

@Preview
@Composable
private fun SlideTopToBottomAnimatedVisibilityPreview() {
    RickAndMortyTheme {
        SlideTopToBottomAnimatedVisibility(visible = true) {
            Icon(
                imageVector = Icons.Filled.AccountBox,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}
