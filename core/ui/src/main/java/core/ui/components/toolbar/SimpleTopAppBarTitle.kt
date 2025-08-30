package core.ui.components.toolbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import core.ui.theme.RickAndMortyTheme
import core.ui.theme.Dimens

@Composable
fun SimpleTopAppBarTitle(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    titleText: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(Dimens.IconSize.AppBar),
            )
            Spacer(modifier = Modifier.width(Dimens.Padding.VerySmall))
        }
        Text(
            text = titleText,
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary,
            ),
        )
    }
}

@Preview
@Composable
private fun SimpleTopAppBarTitlePreview() {
    RickAndMortyTheme {
        SimpleTopAppBarTitle(
            icon = Icons.Default.AccountBox,
            titleText = "Some Screen",
        )
    }
}
