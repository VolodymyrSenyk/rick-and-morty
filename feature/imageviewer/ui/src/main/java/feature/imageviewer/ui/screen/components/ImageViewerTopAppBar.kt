package feature.imageviewer.ui.screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import core.ui.components.topappbar.SimpleTopAppBar
import core.ui.components.topappbar.SimpleTopAppBarNavIcon
import core.ui.components.topappbar.menu.ThemeChoosingMenuItem
import core.ui.theme.RickAndMortyTheme
import domain.settings.model.ThemeMode

@Composable
internal fun ImageViewerTopAppBar(
    modifier: Modifier = Modifier,
    onNavigateBackClicked: () -> Unit,
    onThemeSelected: (ThemeMode) -> Unit,
) {
    SimpleTopAppBar(
        navigationIcon = {
            SimpleTopAppBarNavIcon(
                icon = Icons.Default.Close,
                onClicked = onNavigateBackClicked,
            )
        },
        modifier = modifier
    ) {
        Row {
            ThemeChoosingMenuItem(onThemeSelected = onThemeSelected)
        }
    }
}

@Preview
@Composable
private fun ImageViewerTopAppBarPreview() {
    RickAndMortyTheme {
        ImageViewerTopAppBar(
            onNavigateBackClicked = {},
            onThemeSelected = {},
        )
    }
}
