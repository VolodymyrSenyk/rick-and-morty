package core.ui.components.dialog.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import core.ui.components.dialog.model.DialogButtonData
import core.ui.preview.ThemePreviewParameterProvider
import core.ui.theme.Dimens
import core.ui.theme.RickAndMortyTheme
import domain.settings.model.ThemeMode

private const val SINGLE_BUTTON_WIDTH_COEF = 0.5f

@Composable
fun BaseDialogButtonsRow(
    modifier: Modifier = Modifier,
    outlinedButtonData: DialogButtonData? = null,
    filledButtonData: DialogButtonData? = null,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth(),
    ) {
        val buttonsModifier = if (outlinedButtonData != null && filledButtonData != null) {
            Modifier
                .weight(1f)
                .padding(horizontal = Dimens.Padding.VerySmall)
        } else {
            Modifier
                .fillMaxWidth(SINGLE_BUTTON_WIDTH_COEF)
                .padding(horizontal = Dimens.Padding.VerySmall)
        }

        outlinedButtonData?.let { buttonData ->
            BaseDialogButton(
                buttonData = buttonData,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                ),
                modifier = buttonsModifier
            )
        }

        filledButtonData?.let { buttonData ->
            BaseDialogButton(
                buttonData = buttonData,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                modifier = buttonsModifier
            )
        }
    }
}

@Preview
@Composable
private fun BaseDialogButtonsRowPreview(@PreviewParameter(ThemePreviewParameterProvider::class) themeMode: ThemeMode) {
    RickAndMortyTheme(themeMode) {
        BaseDialogButtonsRow(
            outlinedButtonData = DialogButtonData(
                text = "Cancel",
                onClick = {},
            ),
            filledButtonData = DialogButtonData(
                text = "Continue",
                onClick = {},
            ),
        )
    }
}
