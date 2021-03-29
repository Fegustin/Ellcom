package ru.steilsouth.ellcom.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
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

fun openFile(activity: Activity) {
    val intent = Intent(Intent.ACTION_GET_CONTENT)

    val path =
        Uri.parse((activity.getExternalFilesDir(null)?.path + File.separator) + "score" + File.separator)

    Log.i("PathFile", path.toString())

    intent.setDataAndType(path, "file/*")
    startActivityForResult(activity, intent, 9023, null)
}