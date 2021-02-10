package com.senyk.rickandmorty.domain.repository

import com.senyk.rickandmorty.domain.entity.CharacterDto
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue

interface CharacterRepository {

    @CheckReturnValue
    fun getCharacters(page: Int): Single<List<CharacterDto>>
}
