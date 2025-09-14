package core.ui.components.image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import core.ui.theme.RickAndMortyTheme

@Composable
fun PreviewableAsyncImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    onImageLoaded: () -> Unit = {},
) {
    if (LocalInspectionMode.current) {
        Spacer(modifier.background(color = Color.Red))
    } else {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            contentScale = contentScale,
            onSuccess = {
                onImageLoaded()
            },
            modifier = modifier
        )
    }
}

@Preview
@Composable
private fun PreviewableAsyncImagePreview() {
    RickAndMortyTheme {
        PreviewableAsyncImage(
            imageUrl = "",
        )
    }
}
