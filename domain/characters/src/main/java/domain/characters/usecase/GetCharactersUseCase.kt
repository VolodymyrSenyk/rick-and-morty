package domain.characters.usecase

import domain.characters.CharactersRepository
import domain.characters.model.Character
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository,
) {

    suspend operator fun invoke(page: Int): List<Character> =
        charactersRepository.getCharacters(page)
}
