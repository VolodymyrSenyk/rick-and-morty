package com.senyk.rickandmorty.data.repository

import com.senyk.rickandmorty.data.datasource.network.api.CharactersApi
import com.senyk.rickandmorty.data.datasource.network.entity.CharactersResponseMapper
import com.senyk.rickandmorty.domain.entity.CharacterDto
import com.senyk.rickandmorty.domain.repository.CharacterRepository
import io.reactivex.Single
import javax.inject.Inject

class CharacterNetworkRepository @Inject constructor(
    private val charactersApi: CharactersApi,
    private val charactersResponseMapper: CharactersResponseMapper
) : CharacterRepository {

    override fun getCharacters(page: Int): Single<List<CharacterDto>> =
        charactersApi.getCharacters(page = page).map { charactersResponseMapper(it) }
}
