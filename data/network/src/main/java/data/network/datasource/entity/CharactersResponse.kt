package data.network.datasource.entity

import com.google.gson.annotations.SerializedName
import domain.characters.model.CharacterDto
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
