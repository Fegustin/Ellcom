package com.example.ellcom.pojo.subcontracts

import com.google.gson.annotations.SerializedName

class SubContractsBody(token: String) {
    @SerializedName("method")
    val method: String = "subContractList"

    @SerializedName("params")
    val params = Params(token)

    class Params(token: String) {
        @SerializedName("token")
        val tokenRes: String = token
    }
}