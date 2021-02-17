package ru.steilsouth.ellcom.pojo.email

import com.google.gson.annotations.SerializedName

data class EmailAddress(
    @SerializedName("address") val email: String
)