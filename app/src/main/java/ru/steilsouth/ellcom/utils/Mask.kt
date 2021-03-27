package ru.steilsouth.ellcom.utils

import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener

fun setFormat(format: String, editText: EditText) {
    val listener = MaskedTextChangedListener(format, editText)
    editText.addTextChangedListener(listener)
    editText.onFocusChangeListener = listener
}