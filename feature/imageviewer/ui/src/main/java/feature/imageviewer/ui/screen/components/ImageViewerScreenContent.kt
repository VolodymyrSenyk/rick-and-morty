package feature.imageviewer.ui.screen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import core.ui.components.scaffold.CustomScaffold
import core.ui.preview.ThemePreviewParameterProvider
import core.ui.theme.RickAndMortyTheme
import domain.settings.model.ThemeMode

@Composable
internal fun ImageViewerScreenContent(
    sharedTransitionKey: String,
    url: String,
    contentDescription: String,
    onNavigateBackClicked: () -> Unit,
    onThemeSelected: (ThemeMode) -> Unit,
) {
    CustomScaffold(
        topAppBar = {
            ImageViewerTopAppBar(
                onNavigateBackClicked = onNavigateBackClicked,
                onThemeSelected = onThemeSelected,
            )
        }
    ) {
        ImageViewerSection(
            sharedTransitionKey = sharedTransitionKey,
            url = url,
            contentDescription = contentDescription,
        )
    }
}

@Preview
@Composable
private fun ImageViewerScreenContentPreview(@PreviewParameter(ThemePreviewParameterProvider::class) themeMode: ThemeMode) {
    RickAndMortyTheme(themeMode) {
        ImageViewerScreenContent(
            sharedTransitionKey = "",
            url = "",
            contentDescription = "",
            onNavigateBackClicked = {},
            onThemeSelected = {},
        )
    }
}
