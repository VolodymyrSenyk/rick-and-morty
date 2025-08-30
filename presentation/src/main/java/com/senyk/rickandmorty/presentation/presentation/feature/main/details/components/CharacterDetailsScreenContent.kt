package com.senyk.rickandmorty.presentation.presentation.feature.main.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterDetailsUiMapper
import com.senyk.rickandmorty.presentation.presentation.feature.main.details.components.list.CharacterDetailsList
import com.senyk.rickandmorty.presentation.presentation.feature.main.mvi.CharacterDetailsViewState
import com.senyk.rickandmorty.presentation.presentation.feature.main.preview.CharactersPreviewMocks
import com.senyk.rickandmorty.presentation.presentation.util.provider.ResourcesProvider
import core.ui.theme.BlokNotTheme
import core.ui.theme.Dimens

@Composable
internal fun CharacterDetailsScreenContent(
    modifier: Modifier = Modifier,
    viewState: CharacterDetailsViewState,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = Dimens.Padding.Small)
    ) {
        AsyncImage(
            model = viewState.characterAvatarUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.ImageSize.Big)
                .padding(Dimens.Padding.Small),
        )
        CharacterDetailsList(items = viewState.characterData)
    }
}

@Preview
@Composable
private fun CharacterDetailsScreenContentPreview() {
    BlokNotTheme {
        val context = LocalContext.current
        val resourcesProvider = ResourcesProvider(context)
        val mapper = CharacterDetailsUiMapper(resourcesProvider)
        val characterDetails = mapper(CharactersPreviewMocks.character)
        CharacterDetailsScreenContent(
            viewState = CharacterDetailsViewState(
                characterAvatarUrl = CharactersPreviewMocks.avatarConstructor(),
                characterData = characterDetails,
            ),
        )
    }
}
