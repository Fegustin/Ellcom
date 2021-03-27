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

fun EditText.validationDate(): Boolean {
    val value = text.toString()
    return if (value.isEmpty()) {
        backgroundTintList = ContextCompat.getColorStateList(context, R.color.edit_text_warning)
        false
    } else if (value.substringBefore(".").toInt() > 31 || value.substringAfter(".")
            .substringBefore(".").toInt() > 12
    ) {
        backgroundTintList = ContextCompat.getColorStateList(context, R.color.edit_text_warning)
        false
    } else {
        backgroundTintList = ContextCompat.getColorStateList(context, R.color.edit_text_neutral)
        true
    }
}

fun EditText.validationReallocation(): Boolean {
    return !text.isNullOrEmpty()
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

fun EditText.validationEmailList(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(text).matches()
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