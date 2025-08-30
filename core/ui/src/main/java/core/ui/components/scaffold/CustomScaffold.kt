package core.ui.components.scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import core.ui.components.toolbar.TopAppBarShadow
import core.ui.components.toolbar.SimpleTopAppBarTitle
import core.ui.components.toolbar.SimpleTopAppBar
import core.ui.preview.ThemePreviewParameterProvider
import core.ui.theme.RickAndMortyTheme
import core.ui.theme.ThemeMode

@Composable
fun CustomScaffold(
    modifier: Modifier = Modifier,
    topAppBar: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Surface(color = MaterialTheme.colorScheme.background, modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            topAppBar()
            Box {
                TopAppBarShadow(modifier = Modifier.align(Alignment.TopCenter))
                content()
            }
        }
    }
}

@Preview
@Composable
private fun CustomScaffoldPreview(@PreviewParameter(provider = ThemePreviewParameterProvider::class) themeMode: ThemeMode) {
    RickAndMortyTheme(themeMode) {
        CustomScaffold(
            topAppBar = {
                SimpleTopAppBar(
                    title = {
                        SimpleTopAppBarTitle(titleText = "Some Screen")
                    }
                )
            }
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }
}
