package com.senyk.rickandmorty.presentation.presentation.util.provider

import android.content.Context
import android.content.res.Resources
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourcesProvider @Inject constructor(context: Context) {

    private val resources: Resources = context.resources

    fun getString(@StringRes stringRes: Int): String = resources.getString(stringRes)

    fun getString(@StringRes stringRes: Int, arg1: String): String =
        resources.getString(stringRes, arg1)

    fun getString(@StringRes stringRes: Int, arg1: String, arg2: String): String =
        resources.getString(stringRes, arg1, arg2)

    fun getString(@StringRes stringRes: Int, arg1: String, arg2: String, arg3: String): String =
        resources.getString(stringRes, arg1, arg2, arg3)
}
