package com.example.ellcom.repository

import android.util.Log
import com.example.ellcom.api.ApiUtils
import com.example.ellcom.pojo.auth.AuthBody
import com.example.ellcom.pojo.auth.AuthResult

class AuthRepository {
    private val tag = "Error: class -> AuthRepository: method -> auth"

    suspend fun auth(username: String, password: String, type: Boolean): AuthResult? {
        return try {
            if (type) ApiUtils.apiService.auth(AuthBody(username, password, "authContract"))
            else ApiUtils.apiService.auth(AuthBody(username, password, "authContractByInetLogin"))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }
}