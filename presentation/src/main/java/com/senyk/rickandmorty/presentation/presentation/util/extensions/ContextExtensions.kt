package com.senyk.rickandmorty.presentation.presentation.util.extensions

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager

inline val Context.inputMethodManager: InputMethodManager?
    get() = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

inline val Context.layoutInflater: LayoutInflater
    get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

fun Context.isIntentSafe(intent: Intent) =
    packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).isNotEmpty()
