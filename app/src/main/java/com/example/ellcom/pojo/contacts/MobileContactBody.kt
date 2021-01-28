package com.example.ellcom.pojo.contacts

import com.google.gson.annotations.SerializedName


class MobileContactBody(token: String) {
    @SerializedName("method")
    private val method = "getMobileContacts"

    @SerializedName("params")
    private val params = Params(token)

    class Params(token: String) {
        @SerializedName("token")
        val tokenRes: String = token
    }
}