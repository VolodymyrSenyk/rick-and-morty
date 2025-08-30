package com.senyk.rickandmorty.presentation.presentation.feature.navigation.args

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterUi
import kotlinx.serialization.json.Json

val CharacterNavArgType = object : NavType<CharacterUi>(isNullableAllowed = false) {
    override fun put(bundle: Bundle, key: String, value: CharacterUi) {
        bundle.putParcelable(key, value)
    }

    override fun get(bundle: Bundle, key: String): CharacterUi? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, CharacterUi::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }
    }

    override fun serializeAsValue(value: CharacterUi): String = Uri.encode(Json.encodeToString(value))

    override fun parseValue(value: String): CharacterUi = Json.decodeFromString<CharacterUi>(value)
}
