package core.ui.components.toolbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import core.ui.theme.BlokNotTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    navigationIcon: @Composable (() -> Unit)? = null,
    menu: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        navigationIcon = navigationIcon ?: {},
        title = title,
        actions = menu,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        scrollBehavior = null,
    )
}

@Preview
@Composable
private fun SimpleTopAppBarPreview() {
    BlokNotTheme {
        SimpleTopAppBar(
            title = {
                SimpleTopAppBarTitle(
                    icon = Icons.Default.AccountBox,
                    titleText = "Some Screen",
                )
            }
        )
    }
}
