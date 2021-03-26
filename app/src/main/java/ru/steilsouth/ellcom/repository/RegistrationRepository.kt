package ru.steilsouth.ellcom.repository

import android.util.Log
import com.google.gson.JsonObject
import retrofit2.Response
import ru.steilsouth.ellcom.api.ApiUtils
import ru.steilsouth.ellcom.pojo.registration.RegistrationBody
import ru.steilsouth.ellcom.utils.enam.Token

class RegistrationRepository {
    private val tag = "Error: class -> RegistrationRepository: "

    suspend fun registration(
        name: String,
        organization: String,
        phone: String,
        address: String,
        email: String
    ): Response<JsonObject>? {
        return try {
            ApiUtils.apiCms.registration(
                RegistrationBody(
                    name,
                    organization,
                    phone,
                    address,
                    email
                ),
                Token.CmsEllco.token
            )
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }
}