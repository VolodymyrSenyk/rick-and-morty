package core.ui.components.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.senyk.rickandmorty.core.ui.R
import core.ui.components.dialog.parts.BaseDialogMessage
import core.ui.model.DialogButtonData
import core.ui.preview.ThemePreviewParameterProvider
import core.ui.theme.RickAndMortyTheme
import domain.settings.model.ThemeMode

@Composable
fun BaseInformationalDialogContent(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    iconTint: Color? = null,
    title: String,
    message: String,
    hasOutlinedButton: Boolean = true,
    outlinedButtonText: String? = null,
    onOutlinedButtonClicked: (() -> Unit)? = null,
    filledButtonText: String? = null,
    onFilledButtonClicked: (() -> Unit)? = null,
    onDismiss: () -> Unit,
) {
    BaseDialogContent(
        icon = icon ?: Icons.Default.Info,
        iconTint = iconTint ?: MaterialTheme.colorScheme.onSurface,
        title = title,
        message = {
            BaseDialogMessage(message = message)
        },
        outlinedButtonData = if (hasOutlinedButton) {
            DialogButtonData(
                text = outlinedButtonText ?: stringResource(R.string.dialog_answer_ok),
                onClick = onOutlinedButtonClicked ?: onDismiss,
            )
        } else {
            null
        },
        filledButtonData = if (hasOutlinedButton) {
            null
        } else {
            DialogButtonData(
                text = filledButtonText ?: stringResource(R.string.dialog_answer_ok),
                onClick = onFilledButtonClicked ?: onDismiss,
            )
        },
        onDismiss = onDismiss,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun BaseInformationalDialogContentPreview(@PreviewParameter(ThemePreviewParameterProvider::class) themeMode: ThemeMode) {
    RickAndMortyTheme(themeMode) {
        BaseInformationalDialogContent(
            title = "Lorem ipsum",
            message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            onDismiss = {},
        )
    }
}
