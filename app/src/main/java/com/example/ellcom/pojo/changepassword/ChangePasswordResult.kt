package com.example.ellcom.pojo.changepassword

import com.google.gson.annotations.SerializedName


data class ChangePasswordResult(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)