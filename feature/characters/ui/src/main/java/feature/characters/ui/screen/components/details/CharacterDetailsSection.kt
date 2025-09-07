@file:OptIn(ExperimentalSharedTransitionApi::class)

package feature.characters.ui.screen.components.details

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import core.ui.theme.Dimens
import core.ui.theme.RickAndMortyTheme
import core.ui.utils.WithAnimatedVisibilityScope
import core.ui.utils.WithSharedTransitionScope
import core.ui.utils.isLandscape
import feature.characters.presentation.model.CharacterDetailsUi
import feature.characters.ui.screen.components.details.list.CharacterDetailsList
import feature.characters.ui.screen.preview.CharactersPreviewMocks

@Composable
internal fun CharacterDetailsSection(item: CharacterDetailsUi?) {
    if (item == null) return
    if (isLandscape()) {
        CharacterDetailsSectionLandscape(item = item)
    } else {
        CharacterDetailsSectionPortrait(item = item)
    }
}

@Composable
private fun CharacterDetailsSectionPortrait(
    modifier: Modifier = Modifier,
    item: CharacterDetailsUi,
) {
    WithSharedTransitionScope {
        WithAnimatedVisibilityScope {
            Column(modifier = modifier.padding(horizontal = Dimens.Padding.Small)) {
                val imageShape = MaterialTheme.shapes.large
                if (LocalInspectionMode.current) {
                    Spacer(
                        modifier = Modifier
                            .height(Dimens.ImageSize.Big)
                            .aspectRatio(1f)
                            .padding(Dimens.Padding.Small)
                            .background(color = Color.Red)
                            .align(Alignment.CenterHorizontally)
                            .clip(imageShape)
                            .border(
                                width = Dimens.Size.Border,
                                color = MaterialTheme.colorScheme.outline,
                                shape = imageShape,
                            )
                    )
                } else {
                    AsyncImage(
                        model = item.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .sharedElement(
                                rememberSharedContentState(key = item.uiId + item.imageUrl),
                                animatedVisibilityScope = this@WithAnimatedVisibilityScope
                            )
                            .align(Alignment.CenterHorizontally)
                            .size(Dimens.ImageSize.Big)
                            .padding(Dimens.Padding.Small)
                            .clip(imageShape)
                            .border(
                                width = Dimens.Size.Border,
                                color = MaterialTheme.colorScheme.outline,
                                shape = imageShape,
                            )
                    )
                }
                CharacterDetailsList(character = item)
            }
        }
    }
}

@Composable
private fun CharacterDetailsSectionLandscape(
    modifier: Modifier = Modifier,
    item: CharacterDetailsUi,
) {
    WithSharedTransitionScope {
        WithAnimatedVisibilityScope {
            Row(modifier) {
                if (LocalInspectionMode.current) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
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
                                animatedVisibilityScope = this@WithAnimatedVisibilityScope
                            )
                            .fillMaxHeight()
                            .aspectRatio(1f)
                    )
                }
                CharacterDetailsList(
                    character = item,
                    modifier = Modifier.padding(Dimens.Padding.Medium)
                )
            }
        }
    }
}

@Preview
@Composable
private fun CharacterDetailsSectionPortraitPreview() {
    RickAndMortyTheme {
        CharacterDetailsSectionPortrait(item = CharactersPreviewMocks.characterDetails)
    }
}

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
private fun CharacterDetailsSectionPreview() {
    RickAndMortyTheme {
        CharacterDetailsSectionLandscape(item = CharactersPreviewMocks.characterDetails)
    }
}
