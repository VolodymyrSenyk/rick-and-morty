package domain.characters

import domain.characters.model.CharacterDto
import domain.characters.model.GenderType
import domain.characters.model.StatusType

interface CharacterRepository {

    suspend fun getCharacters(page: Int): List<CharacterDto>

    suspend fun getCharactersByFilter(
        page: Int,
        name: String?,
        status: StatusType?,
        gender: GenderType?,
    ): List<CharacterDto>

    suspend fun getCharacterById(id: String): CharacterDto
}
