package core.ui.components.progress

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import core.ui.theme.BlokNotTheme

@Composable
fun BlockingProgress(
    modifier: Modifier = Modifier,
    visible: Boolean,
    indicatorColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + scaleIn(initialScale = 0.95f),
        exit = fadeOut() + scaleOut(targetScale = 0.95f)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
                .clickable(enabled = true, onClick = {})
                .zIndex(1f),
        ) {
            CircularProgressIndicator(color = indicatorColor)
        }
    }
}

@Preview
@Composable
private fun BlockingProgressPreview() {
    BlokNotTheme {
        BlockingProgress(visible = true)
    }
}
