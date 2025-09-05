package core.ui.components.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.zIndex
import core.ui.preview.TrueFalsePreviewParameterProvider
import core.ui.theme.Dimens
import core.ui.theme.RickAndMortyTheme

@Composable
fun SimpleCircularProgress(
    modifier: Modifier = Modifier,
    blocking: Boolean = false,
    backgroundColor: Color = if (blocking) Color.Black.copy(alpha = 0.3f) else Color.Transparent,
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    indicatorAlignment: Alignment = Alignment.Center,
    indicatorPadding: Dp = Dimens.Padding.Big,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .clickable(enabled = blocking, onClick = {})
            .zIndex(3f),
    ) {
        val progressModifier = Modifier
            .align(indicatorAlignment)
            .padding(indicatorPadding)
        if (LocalInspectionMode.current) {
            CircularProgressIndicator(
                color = indicatorColor,
                progress = { 0.5f },
                modifier = progressModifier
            )
        } else {
            CircularProgressIndicator(
                color = indicatorColor,
                modifier = progressModifier
            )
        }
    }
}

@Preview
@Composable
private fun SimpleCircularProgressPreview(@PreviewParameter(TrueFalsePreviewParameterProvider::class) blocking: Boolean) {
    RickAndMortyTheme {
        SimpleCircularProgress(blocking = blocking)
    }
}
