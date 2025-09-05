@file:OptIn(ExperimentalSharedTransitionApi::class)

package feature.characters.ui.screen.components.details

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
internal fun CharacterDetailsSection(character: CharacterDetailsUi?) {
    if (character == null) return
    if (isLandscape()) {
        CharacterDetailsSectionLandscape(character = character)
    } else {
        CharacterDetailsSectionPortrait(character = character)
    }
}

@Composable
private fun CharacterDetailsSectionPortrait(
    modifier: Modifier = Modifier,
    character: CharacterDetailsUi,
) {
    WithSharedTransitionScope {
        WithAnimatedVisibilityScope {
            Column(modifier = modifier.padding(horizontal = Dimens.Padding.Small)) {
                AsyncImage(
                    model = character.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = character.uiId + character.imageUrl),
                            animatedVisibilityScope = this@WithAnimatedVisibilityScope
                        )
                        .fillMaxWidth()
                        .height(Dimens.ImageSize.Big)
                        .padding(Dimens.Padding.Small)
                )
                CharacterDetailsList(character = character)
            }
        }
    }
}

@Composable
private fun CharacterDetailsSectionLandscape(
    modifier: Modifier = Modifier,
    character: CharacterDetailsUi,
) {
    WithSharedTransitionScope {
        WithAnimatedVisibilityScope {
            Row(modifier = modifier.padding(Dimens.Padding.Small)) {
                AsyncImage(
                    model = character.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = character.uiId + character.imageUrl),
                            animatedVisibilityScope = this@WithAnimatedVisibilityScope
                        )
                        .fillMaxHeight()
                        .padding(Dimens.Padding.Small)
                )
                CharacterDetailsList(character = character)
            }
        }
    }
}

@Preview
@Composable
private fun CharacterDetailsSectionPortraitPreview() {
    RickAndMortyTheme {
        CharacterDetailsSectionPortrait(character = CharactersPreviewMocks.characterDetails)
    }
}

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
private fun CharacterDetailsSectionPreview() {
    RickAndMortyTheme {
        CharacterDetailsSectionLandscape(character = CharactersPreviewMocks.characterDetails)
    }
}
