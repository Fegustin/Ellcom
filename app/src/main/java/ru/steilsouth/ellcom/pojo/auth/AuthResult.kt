package ru.steilsouth.ellcom.pojo.auth

import com.google.gson.annotations.SerializedName

data class AuthResult(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: Data
) {
    data class Data(@SerializedName("return") val res: Result) {
        data class Result(@SerializedName("token") val token: String)
    }
}