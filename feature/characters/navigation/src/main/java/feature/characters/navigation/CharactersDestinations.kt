package feature.characters.navigation

import kotlinx.serialization.Serializable
import navigation.compose.Destination

@Serializable
object CharactersListDestination : Destination

@Serializable
data class CharacterDetailsDestination(val characterId: String) : Destination
