package domain.characters.usecase

import domain.characters.CharacterRepository
import domain.characters.model.CharacterDto
import domain.characters.model.GenderType
import domain.characters.model.StatusType
import javax.inject.Inject

class GetCharactersByFilterUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    suspend operator fun invoke(
        page: Int,
        name: String? = null,
        status: StatusType? = null,
        gender: GenderType? = null,
    ): List<CharacterDto> = characterRepository.getCharactersByFilter(page = page, name = name, status = status, gender = gender)
}
