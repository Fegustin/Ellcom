package ru.steilsouth.ellcom.repository

import android.util.Log
import ru.steilsouth.ellcom.api.ApiUtils
import ru.steilsouth.ellcom.pojo.balance.BalanceResult
import ru.steilsouth.ellcom.pojo.balance.ContractBalanceBody

class BalanceRepository {
    private val tag = "Error: class -> BalanceRepository: "

    suspend fun getBalance(token: String, date: String): BalanceResult? {
        return try {
            ApiUtils.apiService.getBalance(ContractBalanceBody(token, date))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }
}