package ru.steilsouth.ellcom.pojo.balance

import com.google.gson.annotations.SerializedName


data class BalanceResult(
    val status: String,
    val message: String,
    val data: Data
) {
    data class Data(@SerializedName("return") val res: Result) {
        data class Result(
            val incomingSaldo: Float,
            val accounts: Float,
            val accountList: List<BalanceList>,
            val payments: Float,
            val paymentList: List<BalanceList>,
            val charges: Float,
            val chargeList: List<BalanceList>,
            val outgoingSaldo: Float,
            val limit : Float
        )
    }
}

data class BalanceList(
    val type: String,
    val summ: Float
)
