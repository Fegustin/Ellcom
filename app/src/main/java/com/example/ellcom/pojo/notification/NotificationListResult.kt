package com.example.ellcom.pojo.notification

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

data class NotificationListResult(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: Data
) {
    data class Data(
        @SerializedName("return") val res: JsonArray,
        @SerializedName("rowCount") val rowCount: RowCount
        ) {

        data class RowCount(
            @SerializedName("from") val from: Int,
            @SerializedName("count") val count: Int,
            @SerializedName("rowCount") val rowCount: Int
        )

    }
}