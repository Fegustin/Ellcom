package ru.steilsouth.ellcom.pojo

import com.google.gson.annotations.SerializedName


data class TotalReturnValue(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)