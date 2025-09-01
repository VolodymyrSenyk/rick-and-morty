package domain.characters.usecase

import domain.characters.CharacterRepository
import domain.characters.model.CharacterDto
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    suspend operator fun invoke(id: String): CharacterDto =
        characterRepository.getCharacterById(id = id)
}
