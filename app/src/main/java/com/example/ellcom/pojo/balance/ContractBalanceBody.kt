package com.example.ellcom.pojo.balance

import com.google.gson.annotations.SerializedName
import java.util.*

class ContractBalanceBody(token: String, date: String) {
    @SerializedName("method")
    val method: String = "contractBalanceGet"

    @SerializedName("params")
    val params: Params = Params(token, date)

    class Params(token: String, date: String) {
        @SerializedName("token")
        val tokenRes: String = token

        @SerializedName("date")
        val dateRes: String = date
    }
}