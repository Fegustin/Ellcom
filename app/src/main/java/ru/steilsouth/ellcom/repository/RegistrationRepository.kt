package ru.steilsouth.ellcom.repository

import android.util.Log
import com.google.gson.JsonObject
import retrofit2.Response
import ru.steilsouth.ellcom.api.ApiUtils

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