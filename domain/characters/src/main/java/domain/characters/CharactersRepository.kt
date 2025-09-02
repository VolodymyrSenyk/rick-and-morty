package domain.characters

import domain.characters.model.Character
import domain.characters.model.GenderType
import domain.characters.model.StatusType

interface CharactersRepository {

    suspend fun getCharacters(page: Int): List<Character>

    suspend fun getCharactersByFilter(
        page: Int,
        name: String?,
        status: StatusType?,
        gender: GenderType?,
    ): List<Character>

    suspend fun getCharacterById(id: String): Character
}
