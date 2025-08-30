package data.network.datasource.api

import data.network.datasource.entity.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersApi {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharactersResponse
}
