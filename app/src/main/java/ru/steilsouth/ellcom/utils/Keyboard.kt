package ru.steilsouth.ellcom.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

// Скрытие клавиатуры
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
