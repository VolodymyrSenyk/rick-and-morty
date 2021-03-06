package com.senyk.rickandmorty.data.datasource.network.entity

import com.google.gson.annotations.SerializedName
import com.senyk.rickandmorty.domain.entity.CharacterDto
import javax.inject.Inject

data class CharactersResponse(
	@SerializedName("info") val info: InfoResponse?,
	@SerializedName("results") val results: List<CharactersResultsResponse>?
)

class CharactersResponseMapper @Inject constructor(
	private val charactersResultsResponseMapper: CharactersResultsResponseMapper
) {

    operator fun invoke(response: CharactersResponse): List<CharacterDto> =
        response.results?.map { charactersResultsResponseMapper(it) } ?: emptyList()
}
