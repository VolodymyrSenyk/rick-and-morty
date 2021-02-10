package com.senyk.rickandmorty.domain.usecase.orders

import com.senyk.rickandmorty.domain.entity.CharacterDto
import com.senyk.rickandmorty.domain.repository.CharacterRepository
import io.reactivex.Single
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    operator fun invoke(page: Int): Single<List<CharacterDto>> =
        characterRepository.getCharacters(page = page)
}
