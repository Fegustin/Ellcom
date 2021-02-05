package ru.steilsouth.ellcom.utils

import android.util.Patterns
import android.widget.EditText
import androidx.core.content.ContextCompat
import ru.steilsouth.ellcom.R

fun EditText.validation(): Boolean {
    return if (text.isNullOrEmpty()) {
        backgroundTintList = ContextCompat.getColorStateList(context, R.color.edit_text_warning)
        false
    } else {
        backgroundTintList = ContextCompat.getColorStateList(context, R.color.edit_text_neutral)
        true
    }
}

fun EditText.validationPhone(): Boolean {
    return if (text.length < 18) {
        backgroundTintList = ContextCompat.getColorStateList(context, R.color.edit_text_warning)
        false
    } else {
        backgroundTintList = ContextCompat.getColorStateList(context, R.color.edit_text_neutral)
        true
    }
}

fun EditText.validationEmail(): Boolean {
    return if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
        backgroundTintList = ContextCompat.getColorStateList(context, R.color.edit_text_warning)
        false
    } else {
        backgroundTintList = ContextCompat.getColorStateList(context, R.color.edit_text_neutral)
        true
    }
}

fun EditText.validationConfirmPassword(password: String): Boolean {
    return if (text.toString() != password) {
        backgroundTintList = ContextCompat.getColorStateList(context, R.color.edit_text_warning)
        false
    } else {
        backgroundTintList = ContextCompat.getColorStateList(context, R.color.edit_text_neutral)
        true
    }
}