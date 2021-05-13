package ru.steilsouth.ellcom.utils

import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener

// Установка маски для editText
fun setFormat(format: String, editText: EditText) {
    val listener = MaskedTextChangedListener(format, editText)
    editText.addTextChangedListener(listener)
    editText.onFocusChangeListener = listener
}
