package io.seph.presentation.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


fun View.visible() = this.apply {
    visibility = View.VISIBLE
}

fun View.gone() = this.apply {
    visibility = View.GONE
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}