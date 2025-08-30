package core.ui.components.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.zIndex
import core.ui.theme.BlokNotTheme
import core.ui.theme.Dimens

@Composable
fun TopAppBarShadow(
    modifier: Modifier = Modifier,
    elevation: Dp = Dimens.Elevation.AppBar,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height = elevation)
            .zIndex(1f)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Black.copy(alpha = 0.5f), Color.Transparent),
                )
            ),
    )
}

@Preview
@Composable
private fun TopAppBarShadowPreview() {
    BlokNotTheme {
        Column(modifier = Modifier.background(color = Color.White)) {
            SimpleTopAppBar(
                title = {
                    SimpleTopAppBarTitle(titleText = "Some title")
                }
            )
            TopAppBarShadow()
        }
    }
}
