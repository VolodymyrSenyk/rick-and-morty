package data.network.datasource.api

import data.network.datasource.entity.CharactersResponse
import data.network.datasource.entity.CharactersResultsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharactersResponse

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: String): CharactersResultsResponse
}
