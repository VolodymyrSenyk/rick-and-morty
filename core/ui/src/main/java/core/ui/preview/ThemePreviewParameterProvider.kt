package core.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import domain.settings.model.ThemeMode

class ThemePreviewParameterProvider : PreviewParameterProvider<ThemeMode> {
    override val values = sequenceOf(ThemeMode.LIGHT, ThemeMode.DARK)
}
