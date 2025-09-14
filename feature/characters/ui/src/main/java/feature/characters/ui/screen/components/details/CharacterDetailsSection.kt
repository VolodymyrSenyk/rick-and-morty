@file:OptIn(ExperimentalSharedTransitionApi::class)

package feature.characters.ui.screen.components.details

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.senyk.rickandmorty.feature.characters.ui.R
import core.ui.components.image.PreviewableAsyncImage
import core.ui.theme.Dimens
import core.ui.theme.RickAndMortyTheme
import core.ui.utils.WithAnimatedVisibilityScope
import core.ui.utils.WithSharedTransitionScope
import core.ui.utils.border
import core.ui.utils.isLandscape
import feature.characters.presentation.model.CharacterDetailsUi
import feature.characters.ui.screen.components.details.list.CharacterDetailsList
import feature.characters.ui.screen.preview.CharactersPreviewMocks

@Composable
internal fun CharacterDetailsSection(
    item: CharacterDetailsUi?,
    onImageClicked: (sharedTransitionKey: String) -> Unit,
) {
    if (item == null) return
    if (isLandscape()) {
        CharacterDetailsSectionLandscape(
            item = item,
            onImageClicked = onImageClicked,
        )
    } else {
        CharacterDetailsSectionPortrait(
            item = item,
            onImageClicked = onImageClicked,
        )
    }
}

@Composable
private fun CharacterDetailsSectionPortrait(
    modifier: Modifier = Modifier,
    item: CharacterDetailsUi,
    onImageClicked: (sharedTransitionKey: String) -> Unit,
) {
    WithSharedTransitionScope {
        WithAnimatedVisibilityScope {
            Column(modifier = modifier.padding(horizontal = Dimens.Padding.Small)) {
                val imageShape = MaterialTheme.shapes.large
                val sharedTransitionKey = item.uiId + item.imageUrl
                PreviewableAsyncImage(
                    imageUrl = item.imageUrl,
                    contentDescription = stringResource(R.string.content_description_character_image),
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = sharedTransitionKey),
                            animatedVisibilityScope = this@WithAnimatedVisibilityScope
                        )
                        .align(Alignment.CenterHorizontally)
                        .size(Dimens.ImageSize.Big)
                        .padding(Dimens.Padding.Small)
                        .clip(imageShape)
                        .border(shape = imageShape, outline = true)
                        .clickable { onImageClicked(sharedTransitionKey) }
                )
                CharacterDetailsList(character = item)
            }
        }
    }
}

@Composable
private fun CharacterDetailsSectionLandscape(
    modifier: Modifier = Modifier,
    item: CharacterDetailsUi,
    onImageClicked: (sharedTransitionKey: String) -> Unit,
) {
    WithSharedTransitionScope {
        WithAnimatedVisibilityScope {
            Row(modifier) {
                val sharedTransitionKey = item.uiId + item.imageUrl
                PreviewableAsyncImage(
                    imageUrl = item.imageUrl,
                    contentDescription = stringResource(R.string.content_description_character_image),
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = sharedTransitionKey),
                            animatedVisibilityScope = this@WithAnimatedVisibilityScope
                        )
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clickable { onImageClicked(sharedTransitionKey) }
                )
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
        CharacterDetailsSectionPortrait(
            item = CharactersPreviewMocks.characterDetails,
            onImageClicked = {},
        )
    }
}

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
private fun CharacterDetailsSectionPreview() {
    RickAndMortyTheme {
        CharacterDetailsSectionLandscape(
            item = CharactersPreviewMocks.characterDetails,
            onImageClicked = {},
        )
    }
}
