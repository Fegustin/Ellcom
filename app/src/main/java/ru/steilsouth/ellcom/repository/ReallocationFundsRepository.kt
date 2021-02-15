package ru.steilsouth.ellcom.repository

import android.util.Log
import com.google.gson.JsonObject
import ru.steilsouth.ellcom.api.ApiUtils
import ru.steilsouth.ellcom.pojo.changepassword.ChangePasswordResult
import ru.steilsouth.ellcom.pojo.distributefunds.ReallocationFundsBody
import java.math.BigDecimal

class ReallocationFundsRepository {
    private val tag = "Error: class -> ReallocationFundsRepository: "

    suspend fun reallocationOfFunds(token: String, id: Int, amount: BigDecimal): ChangePasswordResult? {
        return try {
            ApiUtils.apiService.reallocationOfFunds(ReallocationFundsBody(token, id, amount))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }
}