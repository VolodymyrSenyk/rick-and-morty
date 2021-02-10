package com.senyk.rickandmorty.data.datasource.network.entity

import com.google.gson.annotations.SerializedName
import com.senyk.rickandmorty.domain.entity.CharacterDto
import com.senyk.rickandmorty.domain.entity.GenderType
import com.senyk.rickandmorty.domain.entity.StatusType
import javax.inject.Inject

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
	@SerializedName("created") val created: String?
)

class CharactersResultsResponseMapper @Inject constructor() {

    operator fun invoke(response: CharactersResultsResponse): CharacterDto = CharacterDto(
		id = response.id ?: 0,
		name = response.name ?: "Name",
		status = when (response.status) {
			"Alive" -> StatusType.ALIVE
			"Dead" -> StatusType.DEAD
			else -> StatusType.UNKNOWN
		},
		species = response.species ?: "",
		type = response.type ?: "",
		gender = when (response.gender) {
			"Female" -> GenderType.FEMALE
			"Male" -> GenderType.MALE
			"Genderless" -> GenderType.GENDERLESS
			else -> GenderType.UNKNOWN
		},
		origin = response.origin?.name ?: "",
		location = response.location?.name ?: "",
		imageUrl = response.url ?: ""
	)
}
