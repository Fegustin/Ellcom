package com.example.ellcom.pojo.changepassword

import com.google.gson.annotations.SerializedName

class ChangePasswordBody(token: String, oldPassword: String, newPassword: String) {
    @SerializedName("method")
    private val method = "getMobileContacts"

    @SerializedName("params")
    private val param = Param(token, oldPassword, newPassword)

    data class Param(
        @SerializedName("token") val token: String,
        @SerializedName("oldPassword") val oldPassword: String,
        @SerializedName("newPassword") val newPassword: String
    )
}