package feature.characters.navigation.args

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Parcelize
@Serializable
data class CharacterNavArg(
    val id: String,
    val uiId: String,
    val name: String,
    val imageUrl: String,
) : Parcelable

val CharacterNavArgType = object : NavType<CharacterNavArg>(isNullableAllowed = false) {
    override fun put(bundle: Bundle, key: String, value: CharacterNavArg) {
        bundle.putParcelable(key, value)
    }

    override fun get(bundle: Bundle, key: String): CharacterNavArg? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, CharacterNavArg::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }
    }

    override fun serializeAsValue(value: CharacterNavArg): String = Uri.encode(Json.encodeToString(value))

    override fun parseValue(value: String): CharacterNavArg = Json.decodeFromString<CharacterNavArg>(value)
}
