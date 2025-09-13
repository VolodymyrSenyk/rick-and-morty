package feature.imageviewer.navigation

import kotlinx.serialization.Serializable
import navigation.compose.Destination

@Serializable
data class ImageViewerDestinations(
    val imageUrl: String,
    val sharedTransitionKey: String = "",
) : Destination
