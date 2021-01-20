package com.example.ellcom.repository

import android.util.Log
import com.example.ellcom.api.ApiUtils
import com.example.ellcom.pojo.contacts.MobileContactBody
import com.example.ellcom.pojo.contacts.MobileContactResult


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