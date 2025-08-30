package feature.characters.model

import feature.characters.R
import feature.characters.util.provider.ResourcesProvider
import javax.inject.Inject

data class CharacterDetailsUi(
    val label: String,
    val data: String
) : ListItem {

    override val viewType: Int = this::class.hashCode()

    override val listId: String = this.label
}

class CharacterDetailsUiMapper @Inject constructor(private val resourcesProvider: ResourcesProvider) {

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
