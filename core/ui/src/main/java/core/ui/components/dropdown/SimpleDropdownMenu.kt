package core.ui.components.dropdown

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import core.ui.theme.Dimens
import core.ui.theme.RickAndMortyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDropdownMenu(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val backgroundColor = MaterialTheme.colorScheme.background
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = selectedOption.orEmpty(),
            onValueChange = { /* read-only field */ },
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface,
            ),
            colors = OutlinedTextFieldDefaults.colors().copy(
                unfocusedContainerColor = backgroundColor,
                focusedContainerColor = backgroundColor,
                unfocusedIndicatorColor = backgroundColor,
                focusedIndicatorColor = backgroundColor,
            ),
            shape = RectangleShape,
            readOnly = true,
            singleLine = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true)
                .fillMaxWidth()
                .wrapContentHeight()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                focusManager.clearFocus()
            },
            shape = RectangleShape,
            containerColor = backgroundColor,
        ) {
            options.forEach { option ->
                if (option == options.first()) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                    Spacer(modifier = Modifier.height(Dimens.Padding.Tiny))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                }
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
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
}

@Preview
@Composable
private fun SimpleDropdownMenuPreview() {
    RickAndMortyTheme {
        SimpleDropdownMenu(
            options = listOf("A", "B", "C"),
            selectedOption = "B",
            onOptionSelected = {},
        )
    }
}
