package feature.characters.ui.screen.components.details.list

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import core.ui.theme.RickAndMortyTheme
import feature.characters.presentation.model.CharacterDetailsUi
import feature.characters.ui.R
import feature.characters.ui.screen.preview.CharactersPreviewMocks

@Composable
internal fun CharacterDetailsList(
    modifier: Modifier = Modifier,
    character: CharacterDetailsUi,
) {
    Column(modifier = modifier) {
        CharacterDetailsItem(
            label = stringResource(R.string.character_name),
            data = character.name,
        )
        CharacterDetailsItem(
            label = stringResource(R.string.character_status),
            data = character.status,
        )
        CharacterDetailsItem(
            label = stringResource(R.string.character_species),
            data = character.species,
        )
        CharacterDetailsItem(
            label = stringResource(R.string.character_type),
            data = character.type,
        )
        CharacterDetailsItem(
            label = stringResource(R.string.character_gender),
            data = character.gender,
        )
        CharacterDetailsItem(
            label = stringResource(R.string.character_origin),
            data = character.origin,
        )
        CharacterDetailsItem(
            label = stringResource(R.string.character_location),
            data = character.location,
        )
    }
}

@Preview
@Composable
private fun CharacterDetailsListPreview() {
    RickAndMortyTheme {
        CharacterDetailsList(character = CharactersPreviewMocks.characterDetails)
    }
}
