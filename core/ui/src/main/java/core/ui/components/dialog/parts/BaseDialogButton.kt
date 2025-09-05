package core.ui.components.dialog.parts

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import core.ui.components.dialog.model.DialogButtonData
import core.ui.theme.Dimens
import core.ui.theme.RickAndMortyTheme

@Composable
fun BaseDialogButton(
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    buttonData: DialogButtonData,
) {
    Button(
        onClick = buttonData.onClick,
        colors = colors,
        contentPadding = PaddingValues(
            horizontal = Dimens.Padding.Small,
            vertical = Dimens.Padding.VerySmall,
        ),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(
            width = Dimens.Size.Border,
            color = MaterialTheme.colorScheme.outlineVariant,
        ),
        modifier = modifier
    ) {
        Text(
            text = buttonData.text,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold,
            ),
        )
    }
}

@Preview
@Composable
private fun BaseDialogButtonPreview() {
    RickAndMortyTheme {
        BaseDialogButton(
            buttonData = DialogButtonData(
                text = "Continue",
                onClick = {},
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
        )
    }
}
