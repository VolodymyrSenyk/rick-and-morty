package feature.characters.ui.screen.components.details.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import core.ui.utils.isLandscape
import feature.characters.presentation.model.CharacterDetailsUi
import feature.characters.ui.screen.preview.CharactersPreviewMocks

@Composable
internal fun AdaptiveCharacterDetails(
    modifier: Modifier = Modifier,
    visible: Boolean,
    character: CharacterDetailsUi?,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        if (character == null) return@AnimatedVisibility
        if (isLandscape()) {
            Row(modifier = modifier.padding(Dimens.Padding.Small)) {
                AsyncImage(
                    model = character.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(Dimens.Padding.Small),
                )
                CharacterDetailsList(character = character)
            }
        } else {
            Column(modifier = modifier.padding(horizontal = Dimens.Padding.Small)) {
                AsyncImage(
                    model = character.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.ImageSize.Big)
                        .padding(Dimens.Padding.Small),
                )
                CharacterDetailsList(character = character)
            }
        }
    }
}

@Preview
@Composable
private fun AdaptiveCharacterDetailsPreview() {
    RickAndMortyTheme {
        AdaptiveCharacterDetails(
            visible = true,
            character = CharactersPreviewMocks.characterDetails,
        )
    }
}

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
private fun AdaptiveCharacterDetailsLandscapePreview() {
    RickAndMortyTheme {
        AdaptiveCharacterDetails(
            visible = true,
            character = CharactersPreviewMocks.characterDetails,
        )
    }
}
