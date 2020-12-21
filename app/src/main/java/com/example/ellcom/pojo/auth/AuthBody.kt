package com.example.ellcom.pojo.auth

import com.google.gson.annotations.SerializedName

class AuthBody(user_name: String, password: String, authBy: String) {
    @SerializedName("method")
    val method: String = authBy

    @SerializedName("params")
    val params = Params(user_name, password)

    class Params(user_name: String, password: String) {
        @SerializedName("login")
        val login: String = user_name

        @SerializedName("pswd")
        val pswd: String = password
    }
}