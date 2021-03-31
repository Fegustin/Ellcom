package ru.steilsouth.ellcom.utils

import android.app.Activity
import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

fun saveFilePDF(data: String, context: Context): String {
    val displayName = "Ellcom ${
        SimpleDateFormat(
            "dd.MM.yyyy HH-mm-ss",
            Locale.getDefault()
        ).format(Calendar.getInstance().time)
    }"
    val decodeData = Base64.decode(data, Base64.DEFAULT)

    if (Build.VERSION.SDK_INT < 29) {
        try {
            val letDirectory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            letDirectory.mkdirs()

            val file = File(letDirectory, displayName + ".pdf")

            FileOutputStream(file).use {
                it.write(decodeData)
            }
        } catch (e: IOException) {
            Log.e("ExternalStorage", "Ошибка сохранения файла " + e)
        }
    } else {
        try {
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
                put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_DOWNLOADS + File.separator + "Ellcom"
                )
            }

            val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

            if (uri != null) {
                resolver.openOutputStream(uri).use {
                    it?.write(decodeData)
                }
            }
        } catch (e: IOException) {
            Log.e("ExternalStorage", "Ошибка сохранения файла ||" + e.localizedMessage)
        }
    }
    return Environment.DIRECTORY_DOWNLOADS + File.separator + "Ellcom"
}

fun openFile(activity: Activity) {
    startActivity(activity, Intent(DownloadManager.ACTION_VIEW_DOWNLOADS), null)
}