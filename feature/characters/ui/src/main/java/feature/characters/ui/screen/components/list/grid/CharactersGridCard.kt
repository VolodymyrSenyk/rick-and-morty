package feature.characters.ui.screen.components.list.grid

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import core.ui.theme.Dimens
import core.ui.theme.RickAndMortyTheme
import core.ui.utils.WithAnimatedVisibilityScope
import core.ui.utils.WithSharedTransitionScope
import feature.characters.presentation.model.CharacterUi
import feature.characters.ui.screen.preview.CharactersPreviewMocks

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun CharactersGridCard(
    modifier: Modifier = Modifier,
    item: CharacterUi,
    onItemClicked: (item: CharacterUi) -> Unit,
) {
    val shape = MaterialTheme.shapes.medium
    WithSharedTransitionScope {
        WithAnimatedVisibilityScope {
            Card(
                shape = shape,
                elevation = CardDefaults.cardElevation(defaultElevation = Dimens.Elevation.Medium),
                modifier = modifier.clickable { onItemClicked(item) }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = item.uiId + item.id),
                            animatedVisibilityScope = this@WithAnimatedVisibilityScope,
                        )
                        .background(color = MaterialTheme.colorScheme.surface, shape = shape)
                ) {
                    if (LocalInspectionMode.current) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .background(color = Color.Red)
                        )
                    } else {
                        AsyncImage(
                            model = item.imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .sharedElement(
                                    rememberSharedContentState(key = item.uiId + item.imageUrl),
                                    animatedVisibilityScope = this@WithAnimatedVisibilityScope,
                                )
                                .fillMaxWidth()
                                .aspectRatio(1f)
                        )
                    }
                    Text(
                        text = item.name,
                        textAlign = TextAlign.Center,
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
                            .padding(vertical = Dimens.Padding.Medium)
                    )
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
