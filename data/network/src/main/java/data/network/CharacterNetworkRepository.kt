package data.network

import data.network.datasource.api.CharactersApi
import data.network.datasource.entity.CharactersResponseMapper
import domain.characters.CharacterRepository
import domain.characters.model.CharacterDto
import javax.inject.Inject

class CharacterNetworkRepository @Inject constructor(
    private val charactersApi: CharactersApi,
    private val charactersResponseMapper: CharactersResponseMapper
) : CharacterRepository {

    override suspend fun getCharacters(page: Int): List<CharacterDto> =
        charactersResponseMapper(charactersApi.getCharacters(page = page))
}
