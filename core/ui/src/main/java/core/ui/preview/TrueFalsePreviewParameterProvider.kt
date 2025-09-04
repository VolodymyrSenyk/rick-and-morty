package core.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class TrueFalsePreviewParameterProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(true, false)
}
