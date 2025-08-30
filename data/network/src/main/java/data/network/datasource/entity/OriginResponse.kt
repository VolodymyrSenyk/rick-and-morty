package data.network.datasource.entity

import com.google.gson.annotations.SerializedName

data class OriginResponse(
	@SerializedName("name") val name: String?,
	@SerializedName("url") val url: String?
)
