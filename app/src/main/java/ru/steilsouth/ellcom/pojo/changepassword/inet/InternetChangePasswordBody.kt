package ru.steilsouth.ellcom.pojo.changepassword.inet

import com.google.gson.annotations.SerializedName

class InternetChangePasswordBody(token: String, servId: Int, oldPassword: String, newPassword: String) {
    @SerializedName("method")
    private val method = "passwordChange"

    @SerializedName("params")
    private val param = Param(token, servId, oldPassword, newPassword)

    data class Param(
        @SerializedName("token") val token: String,
        @SerializedName("servId") val servId: Int,
        @SerializedName("oldPassword") val oldPassword: String,
        @SerializedName("newPassword") val newPassword: String
    )
}