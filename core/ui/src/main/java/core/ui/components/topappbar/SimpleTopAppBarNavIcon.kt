package core.ui.components.topappbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import core.ui.R
import core.ui.theme.RickAndMortyTheme

@Composable
fun SimpleTopAppBarNavIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    onClicked: () -> Unit,
) {
    IconButton(onClick = onClicked, modifier = modifier) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(R.string.menu_item_back),
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Preview
@Composable
private fun SimpleTopAppBarNavIconPreview() {
    RickAndMortyTheme {
        SimpleTopAppBarNavIcon(onClicked = {})
    }
}
