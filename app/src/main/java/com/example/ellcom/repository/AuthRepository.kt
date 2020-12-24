package com.example.ellcom.repository

import android.util.Log
import com.example.ellcom.api.ApiUtils
import com.example.ellcom.pojo.auth.AuthBody
import com.example.ellcom.pojo.auth.AuthResult
import com.example.ellcom.pojo.auth.OneTimePasswordBody
import com.example.ellcom.pojo.forgotpassword.ForgotPasswordBody
import com.example.ellcom.pojo.forgotpassword.ForgotPasswordResult

class AuthRepository {
    private val tag = "Error: class -> AuthRepository: "

    suspend fun auth(username: String, password: String, type: Boolean): AuthResult? {
        return try {
            if (type) ApiUtils.apiService.auth(AuthBody(username, password, "authContract"))
            else ApiUtils.apiService.auth(AuthBody(username, password, "authContractByInetLogin"))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }

    suspend fun forgotPassword(number: String, email: String): ForgotPasswordResult? {
        return try {
            ApiUtils.apiService.forgotPassword(ForgotPasswordBody(number, email))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }

    suspend fun authOneTimePassword(token: String): AuthResult? {
        return try {
            ApiUtils.apiService.authOneTimePassword(OneTimePasswordBody(token))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }
}