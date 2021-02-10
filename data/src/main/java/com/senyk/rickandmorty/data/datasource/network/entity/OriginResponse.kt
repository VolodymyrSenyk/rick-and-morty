package com.senyk.rickandmorty.data.datasource.network.entity

import com.google.gson.annotations.SerializedName

data class OriginResponse(
	@SerializedName("name") val name: String?,
	@SerializedName("url") val url: String?
)
