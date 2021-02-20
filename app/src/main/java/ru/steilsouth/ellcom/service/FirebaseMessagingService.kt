package ru.steilsouth.ellcom.service

import android.content.Context
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.steilsouth.ellcom.repository.MainAndSubRepository

class FirebaseMessagingService : FirebaseMessagingService() {
    private val repositoryNotification = MainAndSubRepository()

    override fun onNewToken(mobileToken: String) {
        super.onNewToken(mobileToken)

        val token = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        CoroutineScope(Dispatchers.IO).launch {
            if (token != null) {
                repositoryNotification.subscribeNotification(token, mobileToken, true)
            }
        }
    }
}