package feature.characters.model

import android.content.Context
import feature.characters.R

data class CharacterDetailsUi(
    val label: String,
    val data: String,
)

internal fun CharacterUi.toCharacterDetailsUiList(context: Context): List<CharacterDetailsUi> = mutableListOf<CharacterDetailsUi>().apply {
    if (name.isNotEmpty()) {
        add(CharacterDetailsUi(label = context.getString(R.string.character_name), data = name))
    }
    if (status.isNotEmpty()) {
        add(CharacterDetailsUi(label = context.getString(R.string.character_status), data = status))
    }
    if (species.isNotEmpty()) {
        add(CharacterDetailsUi(label = context.getString(R.string.character_species), data = species))
    }
    if (type.isNotEmpty()) {
        add(CharacterDetailsUi(label = context.getString(R.string.character_type), data = type))
    }
    if (gender.isNotEmpty()) {
        add(CharacterDetailsUi(label = context.getString(R.string.character_gender), data = gender))
    }
    if (origin.isNotEmpty()) {
        add(CharacterDetailsUi(label = context.getString(R.string.character_origin), data = origin))
    }
    if (location.isNotEmpty()) {
        add(CharacterDetailsUi(label = context.getString(R.string.character_location), data = location))
    }
}
