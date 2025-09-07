package core.ui.components.dropdown

import androidx.compose.foundation.border
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextOverflow
import core.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBoxScope.SimpleExposedDropdownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    onDismissed: () -> Unit,
) {
    val shape = RectangleShape
    val focusManager = LocalFocusManager.current
    ExposedDropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            onDismissed()
            focusManager.clearFocus()
        },
        shape = shape,
        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
        modifier = modifier
            .border(width = Dimens.Size.Border, color = MaterialTheme.colorScheme.outlineVariant)
    ) {
        options.forEach { option ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                onClick = {
                    onOptionSelected(option)
                    focusManager.clearFocus()
                },
                colors = MenuDefaults.itemColors().copy(
                    textColor = MaterialTheme.colorScheme.onSurface,
                )
            )
            if (option != options.last()) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            }
        }
    }
}
