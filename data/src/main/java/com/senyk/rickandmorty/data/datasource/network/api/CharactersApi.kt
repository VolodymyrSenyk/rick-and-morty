package com.senyk.rickandmorty.data.datasource.network.api

import com.senyk.rickandmorty.data.datasource.network.entity.CharactersResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersApi {

    @GET("character")
    fun getCharacters(@Query("page") page: Int): Single<CharactersResponse>
}
