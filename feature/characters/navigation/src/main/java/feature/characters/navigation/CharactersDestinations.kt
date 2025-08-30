package feature.characters.navigation

import feature.characters.navigation.args.CharacterNavArg
import kotlinx.serialization.Serializable
import navigation.compose.Destination

@Serializable
object CharactersListDestination : Destination

@Serializable
data class CharacterDetailsDestination(val character: CharacterNavArg) : Destination
