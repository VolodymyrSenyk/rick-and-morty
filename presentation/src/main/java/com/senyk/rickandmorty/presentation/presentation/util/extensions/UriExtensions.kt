package com.senyk.rickandmorty.presentation.presentation.util.extensions

import android.content.Context
import android.net.Uri
import java.io.InputStream
import java.io.OutputStream
import java.net.URI

fun URI.queryParameterValue(queryName: String): String {
    val parameters = query.split("&")
    var value = ""
    for (parameter in parameters) {
        if (parameter.startsWith(queryName)) {
            value = parameter.substring(queryName.length + 1)
            break
        }
    }
    return value
}

fun Uri.getInputStream(context: Context): InputStream? =
    context.contentResolver.openInputStream(this)

fun Uri.getOutputStream(context: Context): OutputStream? =
    context.contentResolver.openOutputStream(this)
