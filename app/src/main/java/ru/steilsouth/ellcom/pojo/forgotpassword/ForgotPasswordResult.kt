package ru.steilsouth.ellcom.pojo.forgotpassword

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResult(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
)
