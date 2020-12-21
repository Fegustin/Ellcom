package com.example.ellcom.pojo.forgotpassword

import com.google.gson.annotations.SerializedName

class ForgotPasswordBody(contractTitle: String, email: String) {
    @SerializedName("method")
    val method = "passwordReset"

    @SerializedName("params")
    val params = Params(contractTitle, email)

    class Params(contractTitle: String, email: String) {
        @SerializedName("contractTitle")
        val contract = contractTitle

        @SerializedName("email")
        val address = email
    }
}