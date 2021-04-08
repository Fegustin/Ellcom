package ru.steilsouth.ellcom.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat

fun getPermission(
    context: Context,
    permission: String,
    cause: String,
    permissionRequestCode: Int,
    operation: () -> Unit
) {
    when {
        ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED -> {
            operation()
        }
        shouldShowRequestPermissionRationale(context as Activity, permission) -> {
            dialogPermission(context, cause)
        }
        else -> {
            requestPermissions(
                context,
                arrayOf(permission),
                permissionRequestCode
            )
        }
    }
}

private fun dialogPermission(context: Context, cause: String) {
    AlertDialog.Builder(context)
        .setTitle("Разрешения")
        .setMessage(cause)
        .setPositiveButton("Дать разрешение") { _, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val uri: Uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            context.startActivity(intent)
        }
        .setNegativeButton("Отмена") { _, _ -> }
        .show()
}