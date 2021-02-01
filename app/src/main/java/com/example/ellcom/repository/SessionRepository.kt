package com.example.ellcom.repository

import android.util.Log
import com.example.ellcom.api.ApiUtils
import com.example.ellcom.pojo.session.ActiveSessionBody
import com.example.ellcom.pojo.session.HistorySessionBody
import com.example.ellcom.pojo.session.SessionResult

class SessionRepository {
    private val tag = "Error: class -> SessionRepository: "

    suspend fun getActiveSession(token: String, servId: Int): SessionResult? {
        return try {
            ApiUtils.apiService.getActiveSession(ActiveSessionBody(token, servId))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }

    suspend fun getHistorySession(
        token: String,
        servId: Int,
        dateFrom: String,
        dateTo: String
    ): SessionResult? {
        return try {
            ApiUtils.apiService.getHistorySession(
                HistorySessionBody(
                    token,
                    servId,
                    dateFrom,
                    dateTo
                )
            )
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }
}