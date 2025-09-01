package data.network.datasource.entity

import com.google.gson.annotations.SerializedName
import domain.characters.model.CharacterDto

data class CharactersResponse(
    @SerializedName("info") val info: InfoResponse?,
    @SerializedName("results") val results: List<CharactersResultsResponse>?,
)

internal fun CharactersResponse.toCharacterDtoList(): List<CharacterDto> =
    results?.map { it.toCharacterDto() } ?: emptyList()
