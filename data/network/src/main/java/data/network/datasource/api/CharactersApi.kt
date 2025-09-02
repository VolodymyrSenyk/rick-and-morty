package data.network.datasource.api

import data.network.datasource.model.CharacterResponse
import data.network.datasource.model.CharactersListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharactersListResponse

    @GET("character")
    suspend fun getCharactersByFilter(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("status") status: String,
        @Query("gender") gender: String,
    ): CharactersListResponse

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: String): CharacterResponse
}
