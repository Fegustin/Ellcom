package ru.steilsouth.ellcom.pojo.distributefunds

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

class ReallocationFundsBody(token: String, contractId: Int, amount: BigDecimal) {
    @SerializedName("method")
    private val method = "contractBalanceDistribute"

    @SerializedName("params")
    private val params = Params(token, contractId, amount)

    class Params(token: String, contractId: Int, amount: BigDecimal) {
        @SerializedName("token")
        private val tokenRes = token

        @SerializedName("balanceDistributeDataList")
        private val balanceDistributeDataList = listOf(
            BalanceDistributeDataList(contractId, amount)
        )

        class BalanceDistributeDataList(contractId: Int, amount: BigDecimal) {
            @SerializedName("contractId")
            private val contractIdRes = contractId

            @SerializedName("amout")
            private val amout = amount
        }
    }
}