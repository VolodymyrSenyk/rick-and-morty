package com.senyk.rickandmorty.presentation.presentation.util.extensions

import android.content.Context
import android.content.res.Resources.Theme
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

@ColorInt
fun Context.getColorCompat(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

@ColorInt
fun Theme.getThemeColor(colorAttr: Int): Int {
    val typedValue = TypedValue()
    this.resolveAttribute(colorAttr, typedValue, true)
    return typedValue.data
}

fun Theme.getThemeDimenInPixels(dimenAttr: Int): Float {
    val styledAttributes = this.obtainStyledAttributes(intArrayOf(dimenAttr))
    val dimenInPixels = styledAttributes.getDimensionPixelSize(0, 0).toFloat()
    styledAttributes.recycle()
    return dimenInPixels
}

@DrawableRes
fun Theme.getThemeDrawable(drawableAttr: Int): Int {
    val typedValue = TypedValue()
    this.resolveAttribute(drawableAttr, typedValue, true)
    return typedValue.resourceId
}
