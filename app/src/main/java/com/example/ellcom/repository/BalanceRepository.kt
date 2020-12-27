package com.example.ellcom.repository

import android.util.Log
import com.example.ellcom.api.ApiUtils
import com.example.ellcom.pojo.balance.ContractBalanceBody
import com.example.ellcom.pojo.notification.NotificationListResult

class BalanceRepository {
    private val tag = "Error: class -> BalanceRepository: "

    suspend fun getBalance(token: String, date: String): NotificationListResult? {
        return try {
            ApiUtils.apiService.getBalance(ContractBalanceBody(token, date))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }
}