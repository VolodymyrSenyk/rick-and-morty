package core.ui.components.scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import core.ui.components.topappbar.SimpleTopAppBar
import core.ui.components.topappbar.TopAppBarShadow
import core.ui.preview.ThemePreviewParameterProvider
import core.ui.theme.RickAndMortyTheme
import domain.settings.model.ThemeMode

@Composable
fun CustomScaffold(
    modifier: Modifier = Modifier,
    topAppBar: @Composable () -> Unit,
    background: Color = MaterialTheme.colorScheme.background,
    content: @Composable BoxScope.() -> Unit,
) {
    Surface(color = background, modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            topAppBar()
            Box(modifier = Modifier.fillMaxSize()) {
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
        CustomScaffold(topAppBar = { SimpleTopAppBar(titleText = "Some Screen") }) {
            Icon(
                imageVector = Icons.Filled.AccountBox,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .size(100.dp)
                    .align(alignment = Alignment.Center)
            )
        }
    }
}
