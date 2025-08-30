package com.senyk.rickandmorty.data.repository

import com.senyk.rickandmorty.data.datasource.network.api.CharactersApi
import com.senyk.rickandmorty.data.datasource.network.entity.CharactersResponseMapper
import com.senyk.rickandmorty.domain.entity.CharacterDto
import com.senyk.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterNetworkRepository @Inject constructor(
    private val charactersApi: CharactersApi,
    private val charactersResponseMapper: CharactersResponseMapper
) : CharacterRepository {

    override suspend fun getCharacters(page: Int): List<CharacterDto> =
        charactersResponseMapper(charactersApi.getCharacters(page = page))
}
