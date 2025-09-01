package domain.characters

import domain.characters.model.CharacterDto

interface CharacterRepository {

    suspend fun getCharacters(page: Int): List<CharacterDto>

    suspend fun getCharacterById(id: String): CharacterDto
}
