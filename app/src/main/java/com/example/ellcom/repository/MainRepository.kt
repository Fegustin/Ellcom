package com.example.ellcom.repository

import android.util.Log
import com.example.ellcom.api.ApiUtils
import com.example.ellcom.pojo.subcontracts.SubContractsBody
import com.example.ellcom.pojo.subcontracts.SubContractsResult

class MainRepository {
    private val tag = "Error: class -> MainRepository: "

    suspend fun getSubContractsList(token: String): SubContractsResult? {
        return try {
            ApiUtils.apiService.getSubContractsList(SubContractsBody(token))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }
}