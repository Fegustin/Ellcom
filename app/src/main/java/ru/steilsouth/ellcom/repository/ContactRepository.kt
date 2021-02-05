package ru.steilsouth.ellcom.repository

import android.util.Log
import ru.steilsouth.ellcom.api.ApiUtils
import ru.steilsouth.ellcom.pojo.contacts.MobileContactBody
import ru.steilsouth.ellcom.pojo.contacts.MobileContactResult


class ContactRepository {
    private val tag = "Error: class -> ContactRepository: "

    suspend fun getMobileContact(token: String): MobileContactResult? {
        return try {
            ApiUtils.apiService.getMobileContact(MobileContactBody(token))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }
}