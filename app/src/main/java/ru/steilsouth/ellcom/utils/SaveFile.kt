package ru.steilsouth.ellcom.utils

import android.content.Context
import android.util.Base64
import java.io.File
import java.io.FileOutputStream
import java.util.*

fun saveFilePDF(data: String, context: Context): String {
    val letDirectory = File(context.getExternalFilesDir(null), "score")
    letDirectory.mkdirs()
    val file = File(letDirectory, "${Calendar.getInstance(Locale.getDefault()).time}.pdf")
    FileOutputStream(file).use {
        it.write(Base64.decode(data, Base64.DEFAULT))
    }
    return file.path
}