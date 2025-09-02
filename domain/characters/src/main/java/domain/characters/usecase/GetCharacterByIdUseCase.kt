package domain.characters.usecase

import domain.characters.CharactersRepository
import domain.characters.model.Character
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository,
) {

    suspend operator fun invoke(id: String): Character =
        charactersRepository.getCharacterById(id)
}
