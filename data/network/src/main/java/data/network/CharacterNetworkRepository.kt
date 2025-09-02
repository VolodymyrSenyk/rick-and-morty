package data.network

import data.network.datasource.api.CharactersApi
import data.network.datasource.entity.toCharacterDto
import data.network.datasource.entity.toCharacterDtoList
import domain.characters.CharacterRepository
import domain.characters.model.CharacterDto
import domain.characters.model.GenderType
import domain.characters.model.StatusType
import javax.inject.Inject

class CharacterNetworkRepository @Inject constructor(private val charactersApi: CharactersApi) : CharacterRepository {

    override suspend fun getCharacters(page: Int): List<CharacterDto> =
        charactersApi.getCharacters(page = page).toCharacterDtoList()

    override suspend fun getCharactersByFilter(
        page: Int,
        name: String?,
        status: StatusType?,
        gender: GenderType?,
    ): List<CharacterDto> = charactersApi.getCharactersByFilter(
        page = page,
        name = name ?: "",
        status = status?.name?.lowercase() ?: "",
        gender = gender?.name?.lowercase() ?: "",
    ).toCharacterDtoList()

    override suspend fun getCharacterById(id: String): CharacterDto =
        charactersApi.getCharacterById(id = id).toCharacterDto()
}
