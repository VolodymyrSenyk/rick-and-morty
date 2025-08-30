package data.network

import com.senyk.rickandmorty.domain.entity.CharacterDto
import com.senyk.rickandmorty.domain.repository.CharacterRepository
import data.network.datasource.api.CharactersApi
import data.network.datasource.entity.CharactersResponseMapper
import javax.inject.Inject

class CharacterNetworkRepository @Inject constructor(
    private val charactersApi: CharactersApi,
    private val charactersResponseMapper: CharactersResponseMapper
) : CharacterRepository {

    override suspend fun getCharacters(page: Int): List<CharacterDto> =
        charactersResponseMapper(charactersApi.getCharacters(page = page))
}
