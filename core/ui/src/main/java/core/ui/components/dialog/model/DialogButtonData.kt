package core.ui.components.dialog.model

import androidx.compose.runtime.Immutable

@Immutable
data class DialogButtonData(
    val text: String,
    val onClick: () -> Unit,
)
