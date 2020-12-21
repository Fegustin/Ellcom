package com.example.ellcom.pojo.infoprofile

import com.google.gson.annotations.SerializedName

data class RateList(
    @SerializedName("tariffTitle") val tariffTitle: String,
    @SerializedName("dateFrom") val dateFrom: String,
    @SerializedName("dateTo") val dateTo: String,
)