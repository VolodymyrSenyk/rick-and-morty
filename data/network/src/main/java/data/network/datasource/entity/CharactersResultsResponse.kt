package data.network.datasource.entity

import com.google.gson.annotations.SerializedName
import domain.characters.model.CharacterDto

data class CharactersResultsResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("species") val species: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("origin") val origin: OriginResponse?,
    @SerializedName("location") val location: LocationResponse?,
    @SerializedName("image") val image: String?,
    @SerializedName("episode") val episode: List<String>?,
    @SerializedName("url") val url: String?,
    @SerializedName("created") val created: String?,
)

internal fun CharactersResultsResponse.toCharacterDto(): CharacterDto = CharacterDto(
    id = (id ?: System.currentTimeMillis()).toString(),
    name = name ?: "",
    status = status ?: "",
    species = species ?: "",
    type = type ?: "",
    gender = gender ?: "",
    origin = origin?.name ?: "",
    location = location?.name ?: "",
    imageUrl = image ?: "",
)
