package data.network

import data.network.datasource.api.CharactersApi
import data.network.datasource.model.toCharacter
import data.network.datasource.model.toCharactersList
import domain.characters.CharactersRepository
import domain.characters.model.Character
import domain.characters.model.GenderType
import domain.characters.model.StatusType
import javax.inject.Inject

class CharactersNetworkRepository @Inject constructor(
    private val charactersApi: CharactersApi,
) : CharactersRepository {

    override suspend fun getCharacters(page: Int): List<Character> =
        charactersApi.getCharacters(page = page).toCharactersList()

    override suspend fun getCharactersByFilter(
        page: Int,
        name: String?,
        status: StatusType?,
        gender: GenderType?,
    ): List<Character> =
        charactersApi.getCharactersByFilter(
            page = page,
            name = name ?: "",
            status = status?.name?.lowercase() ?: "",
            gender = gender?.name?.lowercase() ?: "",
        ).toCharactersList()

    override suspend fun getCharacterById(id: String): Character =
        charactersApi.getCharacterById(id = id).toCharacter()
}
