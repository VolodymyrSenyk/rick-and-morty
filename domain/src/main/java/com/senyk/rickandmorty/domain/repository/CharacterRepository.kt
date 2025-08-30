package com.senyk.rickandmorty.domain.repository

import com.senyk.rickandmorty.domain.entity.CharacterDto

interface CharacterRepository {

    suspend fun getCharacters(page: Int): List<CharacterDto>
}
