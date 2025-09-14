package feature.imageviewer.ui.screen.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import core.ui.components.image.PreviewableAsyncImage
import core.ui.theme.RickAndMortyTheme
import core.ui.utils.WithAnimatedVisibilityScope
import core.ui.utils.WithSharedTransitionScope
import kotlin.math.max

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun ImageViewerSection(
    modifier: Modifier = Modifier,
    sharedTransitionKey: String = "",
    url: String,
    minScale: Float = 1f,
    maxScale: Float = 5f,
    doubleTapScale: Float = 2.5f,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
) {
    // scale and translation in pixels
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    BoxWithConstraints(modifier = modifier) {
        val boxWidthPx = with(LocalDensity.current) { constraints.maxWidth.toFloat() }
        val boxHeightPx = with(LocalDensity.current) { constraints.maxHeight.toFloat() }

        // helper to clamp translation so image doesn't drift away too far
        fun clampOffsetForScale(currentScale: Float, currentOffset: Offset): Offset {
            // For simplicity assume the image occupies the whole box (ContentScale.Fit/Fill),
            // then the drawable size equals box size; scaled size = box * scale.
            val maxX = max(0f, (boxWidthPx * currentScale - boxWidthPx) / 2f)
            val maxY = max(0f, (boxHeightPx * currentScale - boxHeightPx) / 2f)
            val clampedX = currentOffset.x.coerceIn(-maxX, maxX)
            val clampedY = currentOffset.y.coerceIn(-maxY, maxY)
            return Offset(clampedX, clampedY)
        }

        // Gesture handling: detectTransformGestures handles centroid, pan, zoom, rotation.
        val gestureModifier = Modifier
            .pointerInput(Unit) {
                // Use detectTransformGestures inside the gesture
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    val oldScale = scale
                    val newScaleUnclamped = (scale * zoom)
                    val newScale = newScaleUnclamped.coerceIn(minScale, maxScale)

                    // Adjust offset so zoom is centered on centroid
                    // Translation due to zoom: translate = (offset - centroid) * (newScale/oldScale - 1)
                    val centroidInBox = centroid
                    val zoomFactor = if (oldScale != 0f) (newScale / oldScale) else 1f
                    val offsetFromCentroid = offset - centroidInBox
                    val zoomTranslation = offsetFromCentroid * (zoomFactor - 1f)

                    // Apply pan (in px) and zoom translation
                    val newOffset = offset + pan + zoomTranslation

                    scale = newScale
                    offset = clampOffsetForScale(scale, newOffset)
                }
            }
            .pointerInput(Unit) {
                // Double-tap handler (reset / quick zoom)
                detectTapGestures(
                    onDoubleTap = { tapOffset ->
                        if (scale > minScale + 0.1f) {
                            // reset to default
                            scale = minScale
                            offset = Offset.Zero
                        } else {
                            // zoom into doubleTapScale, center on tapOffset
                            val targetScale = doubleTapScale.coerceIn(minScale, maxScale)
                            // compute offset so that the tapped point goes to center
                            val dx = (boxWidthPx / 2f - tapOffset.x) * (targetScale - 1f)
                            val dy = (boxHeightPx / 2f - tapOffset.y) * (targetScale - 1f)
                            scale = targetScale
                            offset = clampOffsetForScale(scale, Offset(dx, dy))
                        }
                    }
                )
            }

        WithSharedTransitionScope {
            WithAnimatedVisibilityScope {
                val imageModifier = if (LocalInspectionMode.current) {
                    Modifier
                        .fillMaxSize()
                        .aspectRatio(1f)
                } else {
                    Modifier
                        .sharedElement(
                            rememberSharedContentState(key = sharedTransitionKey),
                            animatedVisibilityScope = this@WithAnimatedVisibilityScope,
                        )
                        .fillMaxSize()
                        .then(gestureModifier)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            translationX = offset.x
                            translationY = offset.y
                        }
                }
                PreviewableAsyncImage(
                    imageUrl = url,
                    contentDescription = contentDescription,
                    contentScale = contentScale,
                    modifier = imageModifier
                )
            }
        }
    }
}

@Preview
@Composable
private fun ImageViewerSectionPreview() {
    RickAndMortyTheme {
        ImageViewerSection(url = "")
    }
}
