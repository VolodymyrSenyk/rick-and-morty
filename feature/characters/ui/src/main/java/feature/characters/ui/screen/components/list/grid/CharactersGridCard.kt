package feature.characters.ui.screen.components.list.grid

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil3.imageLoader
import coil3.memory.MemoryCache
import core.ui.components.image.PreviewableAsyncImage
import core.ui.theme.Dimens
import core.ui.theme.RickAndMortyTheme
import core.ui.utils.WithAnimatedVisibilityScope
import core.ui.utils.WithSharedTransitionScope
import core.ui.utils.border
import feature.characters.presentation.model.CharacterUi
import feature.characters.ui.screen.preview.CharactersPreviewMocks

private const val FlipAnimDuration = 600
private const val FullFlip = 180f

@Composable
internal fun CharactersGridCard(
    modifier: Modifier = Modifier,
    item: CharacterUi,
    onItemClicked: (item: CharacterUi) -> Unit,
) {
    val imageLoader = LocalContext.current.imageLoader
    val cached = imageLoader.memoryCache?.keys?.contains(MemoryCache.Key(item.imageUrl)) == true
    var imageLoaded by remember { mutableStateOf(false) }
    var flipRotation by remember { mutableFloatStateOf(if (cached) FullFlip else 0f) }
    if (LocalInspectionMode.current) {
        flipRotation = FullFlip
    }
    if (imageLoaded) {
        LaunchedEffect(Unit) {
            animate(
                initialValue = 0f,
                targetValue = FullFlip,
                animationSpec = tween(
                    durationMillis = FlipAnimDuration,
                    easing = CubicBezierEasing(0.4f, 0.0f, 0.8f, 0.8f),
                ),
            ) { value: Float, _: Float ->
                flipRotation = value
            }
        }
    }
    val cardModifier = modifier
        .layoutId("FlipCard")
        .graphicsLayer {
            rotationY = flipRotation
            cameraDistance = 8 * density
        }
    if (flipRotation < FullFlip / 2) {
        CharactersGridCard(
            item = item,
            showPlaceholder = true,
            onItemClicked = {},
            onImageLoaded = { imageLoaded = true },
            modifier = cardModifier
        )
    } else {
        CharactersGridCard(
            item = item,
            showPlaceholder = false,
            onItemClicked = onItemClicked,
            onImageLoaded = {},
            modifier = cardModifier.graphicsLayer {
                rotationY = FullFlip
            }
        )
    }
}

@Composable
private fun CharactersGridCard(
    modifier: Modifier = Modifier,
    item: CharacterUi,
    showPlaceholder: Boolean,
    onItemClicked: (item: CharacterUi) -> Unit,
    onImageLoaded: () -> Unit,
) {
    val shape = MaterialTheme.shapes.medium
    val maxLinesCount = 2
    val textStyle = MaterialTheme.typography.bodyMedium.copy(
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Bold,
    )
    val textBlockHeight = with(LocalDensity.current) {
        textStyle.fontSize.toDp() * maxLinesCount + Dimens.Padding.Small * 2
    }
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val cardHeight = maxWidth + textBlockHeight
        val cardModifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
        CardContent(
            item = item,
            textStyle = textStyle,
            onImageLoaded = onImageLoaded,
            onItemClicked = onItemClicked,
            modifier = cardModifier
        )
        if (showPlaceholder) {
            Spacer(cardModifier.background(color = MaterialTheme.colorScheme.surfaceDim, shape = shape))
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun CardContent(
    modifier: Modifier = Modifier,
    item: CharacterUi,
    textStyle: TextStyle,
    onItemClicked: (item: CharacterUi) -> Unit,
    onImageLoaded: () -> Unit,
) {
    WithSharedTransitionScope {
        WithAnimatedVisibilityScope {
            val shape = MaterialTheme.shapes.medium
            Card(
                shape = shape,
                elevation = CardDefaults.cardElevation(defaultElevation = Dimens.Elevation.Medium),
                modifier = modifier.clickable { onItemClicked(item) }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .sharedBounds(
                            rememberSharedContentState(key = item.uiId + item.id),
                            animatedVisibilityScope = this@WithAnimatedVisibilityScope,
                        )
                        .background(color = MaterialTheme.colorScheme.surface, shape = shape)
                        .border(shape)
                ) {
                    val imageShape = shape.copy(
                        bottomStart = ZeroCornerSize,
                        bottomEnd = ZeroCornerSize,
                    )
                    PreviewableAsyncImage(
                        imageUrl = item.imageUrl,
                        onImageLoaded = onImageLoaded,
                        modifier = Modifier
                            .sharedElement(
                                rememberSharedContentState(key = item.uiId + item.imageUrl),
                                animatedVisibilityScope = this@WithAnimatedVisibilityScope,
                            )
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(imageShape)
                            .padding(Dimens.Size.Border)
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = item.name,
                        textAlign = TextAlign.Center,
                        style = textStyle,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .sharedElement(
                                rememberSharedContentState(key = item.uiId + item.name),
                                animatedVisibilityScope = this@WithAnimatedVisibilityScope,
                            )
                            .padding(horizontal = Dimens.Padding.Tiny)
                            .animateContentSize()
                    )
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview
@Composable
private fun CharactersGridCardPreview() {
    RickAndMortyTheme {
        CharactersGridCard(
            item = CharactersPreviewMocks.character,
            onItemClicked = {},
        )
    }
}

@Preview
@Composable
private fun CharactersGridCardPlaceholderPreview() {
    RickAndMortyTheme {
        CharactersGridCard(
            item = CharactersPreviewMocks.character,
            showPlaceholder = true,
            onItemClicked = {},
            onImageLoaded = {},
        )
    }
}
