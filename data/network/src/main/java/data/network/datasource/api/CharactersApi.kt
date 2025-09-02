package data.network.datasource.api

import data.network.datasource.entity.CharactersResponse
import data.network.datasource.entity.CharactersResultsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharactersResponse

    @GET("character")
    suspend fun getCharactersByFilter(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("status") status: String,
        @Query("gender") gender: String,
    ): CharactersResponse

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: String): CharactersResultsResponse
}
