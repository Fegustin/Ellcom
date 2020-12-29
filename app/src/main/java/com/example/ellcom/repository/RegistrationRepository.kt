package com.example.ellcom.repository

import android.util.Log
import com.example.ellcom.api.ApiUtils
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Response

class RegistrationRepository {
    private val tag = "Error: class -> RegistrationRepository: "

    suspend fun registration(
        name: String,
        nameCompany: String,
        phone: String,
        address: String,
        email: String
    ): Response<JsonObject>? {
        return try {
            ApiUtils.apiRegistration.registration(name, nameCompany, phone, address, email)
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }
}