package com.senyk.rickandmorty.presentation.presentation.entity

import android.os.Parcelable
import com.senyk.rickandmorty.presentation.R
import com.senyk.rickandmorty.presentation.presentation.util.provider.ResourcesProvider
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
data class CharacterDetailsUi(
    val label: String,
    val data: String
) : ListItem, Parcelable {

    @IgnoredOnParcel
    override val viewType: Int = this::class.hashCode()

    @IgnoredOnParcel
    override val listId: String = this.label
}

class CharacterDetailsUiMapper @Inject constructor(
    private val resourcesProvider: ResourcesProvider
) {

    operator fun invoke(model: CharacterUi): List<CharacterDetailsUi> =
        mutableListOf<CharacterDetailsUi>().apply {

            if (model.name.isNotEmpty()) {
                add(
                    CharacterDetailsUi(
                        label = resourcesProvider.getString(R.string.character_name),
                        data = model.name
                    )
                )
            }

            if (model.status.isNotEmpty()) {
                add(
                    CharacterDetailsUi(
                        label = resourcesProvider.getString(R.string.character_status),
                        data = model.status
                    )
                )
            }

            if (model.species.isNotEmpty()) {
                add(
                    CharacterDetailsUi(
                        label = resourcesProvider.getString(R.string.character_species),
                        data = model.species
                    )
                )
            }

            if (model.type.isNotEmpty()) {
                add(
                    CharacterDetailsUi(
                        label = resourcesProvider.getString(R.string.character_type),
                        data = model.type
                    )
                )
            }

            if (model.gender.isNotEmpty()) {
                add(
                    CharacterDetailsUi(
                        label = resourcesProvider.getString(R.string.character_gender),
                        data = model.gender
                    )
                )
            }

            if (model.origin.isNotEmpty()) {
                add(
                    CharacterDetailsUi(
                        label = resourcesProvider.getString(R.string.character_origin),
                        data = model.origin
                    )
                )
            }

            if (model.location.isNotEmpty()) {
                add(
                    CharacterDetailsUi(
                        label = resourcesProvider.getString(R.string.character_location),
                        data = model.location
                    )
                )
            }
        }
}
