package feature.characters.ui.screen.components.details.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import core.ui.theme.RickAndMortyTheme
import feature.characters.presentation.model.CharacterDetailsUi
import feature.characters.ui.R
import feature.characters.ui.screen.preview.CharactersPreviewMocks

@Composable
internal fun ColumnScope.CharacterDetailsList(
    modifier: Modifier = Modifier,
    character: CharacterDetailsUi,
) {
    CharacterDetailsItem(
        label = stringResource(R.string.character_name),
        data = character.name,
        modifier = modifier,
    )
    CharacterDetailsItem(
        label = stringResource(R.string.character_status),
        data = character.status,
        modifier = modifier,
    )
    CharacterDetailsItem(
        label = stringResource(R.string.character_species),
        data = character.species,
        modifier = modifier,
    )
    CharacterDetailsItem(
        label = stringResource(R.string.character_type),
        data = character.type,
        modifier = modifier,
    )
    CharacterDetailsItem(
        label = stringResource(R.string.character_gender),
        data = character.gender,
        modifier = modifier,
    )
    CharacterDetailsItem(
        label = stringResource(R.string.character_origin),
        data = character.origin,
        modifier = modifier,
    )
    CharacterDetailsItem(
        label = stringResource(R.string.character_location),
        data = character.location,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun CharacterDetailsListPreview() {
    RickAndMortyTheme {
        Column {
            CharacterDetailsList(character = CharactersPreviewMocks.characterDetails)
        }
    }
}
