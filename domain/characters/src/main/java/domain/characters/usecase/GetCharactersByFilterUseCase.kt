package domain.characters.usecase

import domain.characters.CharactersRepository
import domain.characters.model.Character
import domain.characters.model.GenderType
import domain.characters.model.StatusType
import javax.inject.Inject

class GetCharactersByFilterUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository,
) {

    suspend operator fun invoke(
        page: Int,
        name: String? = null,
        status: StatusType? = null,
        gender: GenderType? = null,
    ): List<Character> =
        charactersRepository.getCharactersByFilter(
            page = page,
            name = name,
            status = status,
            gender = gender,
        )
}
