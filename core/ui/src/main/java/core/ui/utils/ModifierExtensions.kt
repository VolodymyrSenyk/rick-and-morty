package core.ui.utils

import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import core.ui.theme.Dimens

@Composable
fun Modifier.border(
    shape: Shape,
    outline: Boolean = false,
): Modifier =
    this.border(
        width = Dimens.Size.Border,
        color = if (outline) {
            MaterialTheme.colorScheme.outline
        } else {
            MaterialTheme.colorScheme.outlineVariant
        },
        shape = shape,
    )
