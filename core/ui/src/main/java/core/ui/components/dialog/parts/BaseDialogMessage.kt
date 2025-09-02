package core.ui.components.dialog.parts

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import core.ui.preview.ThemePreviewParameterProvider
import core.ui.theme.Dimens
import core.ui.theme.RickAndMortyTheme
import domain.settings.model.ThemeMode

@Composable
internal fun BaseDialogMessage(
    modifier: Modifier = Modifier,
    message: String,
) {
    Text(
        text = message,
        style = MaterialTheme.typography.bodySmall.copy(
            fontWeight = FontWeight.Normal,
        ),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier.padding(horizontal = Dimens.Padding.Small)
    )
}

@Preview
@Composable
private fun BaseDialogMessagePreview(@PreviewParameter(provider = ThemePreviewParameterProvider::class) themeMode: ThemeMode) {
    RickAndMortyTheme(themeMode) {
        BaseDialogMessage(
            message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        )
    }
}
