package com.example.ellcom.pojo.auth

import com.google.gson.annotations.SerializedName

class OneTimePasswordBody(passwordOnce: String) {
    @SerializedName("method")
    private val method = "authContractByPasswordOnce"

    @SerializedName("params")
    private val params = Params(passwordOnce)

    class Params(passwordOnce: String) {
        @SerializedName("passwordOnce")
        private val tokenPassword = passwordOnce
    }
}