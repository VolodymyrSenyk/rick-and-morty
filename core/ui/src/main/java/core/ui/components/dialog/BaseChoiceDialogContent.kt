package core.ui.components.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import core.ui.R
import core.ui.components.dialog.model.DialogButtonData
import core.ui.components.dialog.parts.BaseDialogMessage
import core.ui.preview.ThemePreviewParameterProvider
import core.ui.theme.RickAndMortyTheme
import domain.settings.model.ThemeMode

@Composable
fun BaseChoiceDialogContent(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    iconTint: Color? = null,
    title: String,
    message: String,
    outlinedButtonText: String? = null,
    onOutlinedButtonClicked: (() -> Unit)? = null,
    filledButtonText: String? = null,
    onFilledButtonClicked: (() -> Unit)? = null,
    onDismiss: () -> Unit,
) {
    BaseDialogContent(
        icon = icon ?: Icons.Default.Warning,
        iconTint = iconTint ?: MaterialTheme.colorScheme.onSurface,
        title = title,
        message = {
            BaseDialogMessage(message = message)
        },
        outlinedButtonData = DialogButtonData(
            text = outlinedButtonText ?: stringResource(R.string.dialog_answer_cancel),
            onClick = onOutlinedButtonClicked ?: onDismiss,
        ),
        filledButtonData = DialogButtonData(
            text = filledButtonText ?: stringResource(R.string.dialog_answer_continue),
            onClick = onFilledButtonClicked ?: onDismiss,
        ),
        onDismiss = onDismiss,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun BaseChoiceDialogContentPreview(@PreviewParameter(ThemePreviewParameterProvider::class) themeMode: ThemeMode) {
    RickAndMortyTheme(themeMode) {
        BaseChoiceDialogContent(
            title = "Lorem ipsum",
            message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            onDismiss = {},
        )
    }
}
