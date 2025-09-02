package core.ui.components.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import core.ui.components.dialog.model.DialogButtonData
import core.ui.components.dialog.parts.BaseDialogButtonsRow
import core.ui.components.dialog.parts.BaseDialogMessage
import core.ui.components.dialog.parts.BaseDialogTitle
import core.ui.preview.ThemePreviewParameterProvider
import core.ui.theme.Dimens
import core.ui.theme.RickAndMortyTheme
import domain.settings.model.ThemeMode

@Composable
fun BaseDialogContent(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    iconTint: Color? = null,
    title: String,
    outlinedButtonData: DialogButtonData? = null,
    filledButtonData: DialogButtonData? = null,
    onDismiss: () -> Unit,
    message: @Composable ColumnScope.() -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false),
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            modifier = modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(Dimens.Padding.VerySmall)) {
                Spacer(modifier = Modifier.height(Dimens.Padding.Medium))
                BaseDialogTitle(
                    title = title,
                    icon = icon,
                    iconTint = iconTint,
                )
                Spacer(modifier = Modifier.height(Dimens.Padding.VerySmall))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Spacer(modifier = Modifier.height(Dimens.Padding.Small))
                message()
                Spacer(modifier = Modifier.height(Dimens.Padding.Small))
                BaseDialogButtonsRow(
                    outlinedButtonData = outlinedButtonData,
                    filledButtonData = filledButtonData,
                )
            }
        }
    }
}

@Preview
@Composable
private fun BaseDialogContentPreview(@PreviewParameter(provider = ThemePreviewParameterProvider::class) themeMode: ThemeMode) {
    RickAndMortyTheme(themeMode) {
        BaseDialogContent(
            icon = Icons.Default.Warning,
            title = "Lorem ipsum",
            message = {
                BaseDialogMessage(
                    message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                )
            },
            outlinedButtonData = DialogButtonData(
                text = "Cancel",
                onClick = {},
            ),
            filledButtonData = DialogButtonData(
                text = "Continue",
                onClick = {},
            ),
            onDismiss = {},
        )
    }
}
