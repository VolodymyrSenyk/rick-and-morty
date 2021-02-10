package com.senyk.rickandmorty.presentation.presentation.util.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import androidx.fragment.app.Fragment

fun View.showKeyboard() {
    post {
        requestFocus()
        context.inputMethodManager?.showSoftInput(this@showKeyboard, SHOW_IMPLICIT)
    }
}

fun View.hideKeyboard() {
    post {
        context.inputMethodManager?.hideSoftInputFromWindow(windowToken, HIDE_NOT_ALWAYS)
    }
}

fun Activity.showKeyboard() = currentFocus?.showKeyboard()

fun Activity.hideKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = currentFocus ?: View(this)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.showKeyboard() = activity?.showKeyboard()

fun Fragment.hideKeyboard() = activity?.hideKeyboard()
