package com.senyk.rickandmorty.presentation.presentation.util.extensions

import android.text.InputFilter
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes

fun TextView.isEmpty() = text.isEmpty()

fun TextView.setMaxLength(maxLength: Int) {
    val maxLengthFilter = InputFilter.LengthFilter(maxLength)

    filters.forEachIndexed { i, inputFilter ->
        if (inputFilter is InputFilter.LengthFilter) {
            filters[i] = maxLengthFilter
            return
        }
    }

    filters = filters.toMutableList().apply { add(maxLengthFilter) }.toTypedArray()
}

inline val View.layoutInflater get() = context.layoutInflater

fun ViewGroup.inflateView(@LayoutRes layoutId: Int, attachToRoot: Boolean = false): View =
    layoutInflater.inflate(layoutId, this, attachToRoot)
