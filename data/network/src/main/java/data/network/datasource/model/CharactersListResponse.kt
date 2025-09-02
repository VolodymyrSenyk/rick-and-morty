package data.network.datasource.model

import com.google.gson.annotations.SerializedName
import domain.characters.model.Character

data class CharactersListResponse(
    @SerializedName("info") val info: InfoResponse?,
    @SerializedName("results") val results: List<CharacterResponse>?,
)

internal fun CharactersListResponse.toCharactersList(): List<Character> =
    results?.map { it.toCharacter() } ?: emptyList()
