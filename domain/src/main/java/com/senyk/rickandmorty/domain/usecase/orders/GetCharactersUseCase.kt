package com.senyk.rickandmorty.domain.usecase.orders

import com.senyk.rickandmorty.domain.entity.CharacterDto
import com.senyk.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(page: Int): List<CharacterDto> =
        characterRepository.getCharacters(page = page)
}
