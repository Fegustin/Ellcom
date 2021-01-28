package com.example.ellcom.repository

import android.util.Log
import com.example.ellcom.api.ApiUtils
import com.example.ellcom.pojo.changepassword.ChangePasswordBody
import com.example.ellcom.pojo.changepassword.ChangePasswordResult
import com.example.ellcom.pojo.changepassword.inet.ServiceInternetBody
import com.example.ellcom.pojo.changepassword.inet.ServiceInternetResult
import com.google.gson.JsonObject

class ChangePasswordRepository {
    private val tag = "Error: class -> ChangePasswordRepository: "

    suspend fun passwordChange(
        token: String,
        oldPassword: String,
        newPassword: String
    ): ChangePasswordResult? {
        return try {
            ApiUtils.apiService.passwordReset(ChangePasswordBody(token, oldPassword, newPassword))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }

    suspend fun getListServiceInternet(
        token: String
    ): ServiceInternetResult? {
        return try {
            ApiUtils.apiService.getServiceInternet(ServiceInternetBody(token))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }
}