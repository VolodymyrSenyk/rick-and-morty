package feature.characters.ui.screen.components.search.list

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import core.ui.components.image.PreviewableAsyncImage
import core.ui.theme.Dimens
import core.ui.theme.RickAndMortyTheme
import core.ui.utils.WithAnimatedVisibilityScope
import core.ui.utils.WithSharedTransitionScope
import core.ui.utils.border
import feature.characters.presentation.model.CharacterUi
import feature.characters.ui.screen.preview.CharactersPreviewMocks

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun CharactersSearchListCard(
    modifier: Modifier = Modifier,
    item: CharacterUi,
    onItemClicked: (item: CharacterUi) -> Unit,
) {
    val shape = MaterialTheme.shapes.small
    Card(
        shape = shape,
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.Elevation.Medium),
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.CardSize.Small)
            .clickable { onItemClicked(item) }
    ) {
        WithSharedTransitionScope {
            WithAnimatedVisibilityScope {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = item.uiId + item.id),
                            animatedVisibilityScope = this@WithAnimatedVisibilityScope,
                        )
                        .background(color = MaterialTheme.colorScheme.surfaceContainerLow, shape = shape)
                        .border(shape)
                ) {
                    val imageShape = shape.copy(
                        topEnd = ZeroCornerSize,
                        bottomEnd = ZeroCornerSize,
                    )
                    PreviewableAsyncImage(
                        imageUrl = item.imageUrl,
                        modifier = Modifier
                            .sharedElement(
                                rememberSharedContentState(key = item.uiId + item.imageUrl),
                                animatedVisibilityScope = this@WithAnimatedVisibilityScope,
                            )
                            .fillMaxHeight()
                            .aspectRatio(1f)
                            .clip(imageShape)
                            .padding(Dimens.Size.Border)
                    )
                    Text(
                        text = item.name,
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier
                            .sharedElement(
                                rememberSharedContentState(key = item.uiId + item.name),
                                animatedVisibilityScope = this@WithAnimatedVisibilityScope,
                            )
                            .fillMaxWidth()
                            .padding(Dimens.Padding.VerySmall)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CharactersSearchListCardPreview() {
    RickAndMortyTheme {
        CharactersSearchListCard(
            item = CharactersPreviewMocks.character,
            onItemClicked = {},
        )
    }
}
