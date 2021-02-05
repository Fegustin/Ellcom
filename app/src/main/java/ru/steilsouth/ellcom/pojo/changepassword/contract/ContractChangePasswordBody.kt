package ru.steilsouth.ellcom.pojo.changepassword.contract

import com.google.gson.annotations.SerializedName

class ContractChangePasswordBody(token: String, oldPassword: String, newPassword: String) {
    @SerializedName("method")
    private val method = "passwordChange"

    @SerializedName("params")
    private val param = Param(token, oldPassword, newPassword)

    data class Param(
        @SerializedName("token") val token: String,
        @SerializedName("oldPassword") val oldPassword: String,
        @SerializedName("newPassword") val newPassword: String
    )
}