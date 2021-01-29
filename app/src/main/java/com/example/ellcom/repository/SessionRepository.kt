package com.example.ellcom.repository

import android.util.Log
import com.example.ellcom.api.ApiUtils
import com.example.ellcom.pojo.session.ActiveSessionBody
import com.google.gson.JsonObject

class SessionRepository {
    private val tag = "Error: class -> SessionRepository: "

    suspend fun getActiveSession(token: String, servId: Int): JsonObject? {
        return try {
            ApiUtils.apiService.getActiveSession(ActiveSessionBody(token, servId))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }
}