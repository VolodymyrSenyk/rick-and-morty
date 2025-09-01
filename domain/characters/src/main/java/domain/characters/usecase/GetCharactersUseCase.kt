package domain.characters.usecase

import domain.characters.CharacterRepository
import domain.characters.model.CharacterDto
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    suspend operator fun invoke(page: Int): List<CharacterDto> =
        characterRepository.getCharacters(page = page)
}
