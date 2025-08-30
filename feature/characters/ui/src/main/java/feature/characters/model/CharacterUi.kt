package feature.characters.model

import com.senyk.rickandmorty.domain.entity.CharacterDto
import com.senyk.rickandmorty.domain.entity.GenderType
import com.senyk.rickandmorty.domain.entity.StatusType
import feature.characters.R
import feature.characters.util.provider.ResourcesProvider
import javax.inject.Inject

data class CharacterUi(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: String,
    val location: String,
    val imageUrl: String
) : ListItem {

    override val viewType: Int = this::class.hashCode()

    override val listId: String = this.id.toString()
}

class CharacterUiMapper @Inject constructor(private val resourcesProvider: ResourcesProvider) {

    operator fun invoke(dto: CharacterDto): CharacterUi = CharacterUi(
        id = dto.id,
        name = dto.name,
        status = when (dto.status) {
            StatusType.ALIVE -> resourcesProvider.getString(R.string.state_alive)
            StatusType.DEAD -> resourcesProvider.getString(R.string.state_dead)
            StatusType.UNKNOWN -> resourcesProvider.getString(R.string.state_unknown)
        },
        species = dto.species,
        type = dto.type,
        gender = when (dto.gender) {
            GenderType.FEMALE -> resourcesProvider.getString(R.string.gender_female)
            GenderType.MALE -> resourcesProvider.getString(R.string.gender_male)
            GenderType.GENDERLESS -> resourcesProvider.getString(R.string.gender_genderless)
            GenderType.UNKNOWN -> resourcesProvider.getString(R.string.gender_unknown)
        },
        origin = dto.origin,
        location = dto.location,
        imageUrl = dto.imageUrl
    )
}
