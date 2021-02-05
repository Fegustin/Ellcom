package ru.steilsouth.ellcom.pojo.changepassword.inet

import com.google.gson.annotations.SerializedName

class ServiceInternetBody(token: String) {
    @SerializedName("method")
    private val method = "getInetServList"

    @SerializedName("params")
    private val param = Param(token)

    data class Param(
        @SerializedName("token") val token: String,
    )
}