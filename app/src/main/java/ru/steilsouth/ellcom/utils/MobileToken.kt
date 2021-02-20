package ru.steilsouth.ellcom.utils

import android.content.Context
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.steilsouth.ellcom.repository.MainAndSubRepository

fun subscribeNotification(context: Context, subscribe: Boolean) {
    val token =
        context.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            return@OnCompleteListener
        }

        val mobileToken = task.result

        CoroutineScope(Dispatchers.IO).launch {
            if (mobileToken != null && token != null) {
                MainAndSubRepository().subscribeNotification(token, mobileToken, subscribe)
            }
        }
    })
}