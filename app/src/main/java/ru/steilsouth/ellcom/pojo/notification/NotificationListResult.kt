package ru.steilsouth.ellcom.pojo.notification

import com.google.gson.annotations.SerializedName

data class NotificationListResult(
    val status: String,
    val message: String,
    val data: Data
) {
    data class Data(
        @SerializedName("return") val res: List<MessageNotification>,
        val rowCount: RowCount
    )
}

data class MessageNotification(
    val id: Int,
    val contractId: Int,
    val title: String,
    val text: String,
    val status: String,
    val storable: Boolean,
    val dateSend: Long
)

data class RowCount(
    val from: Int,
    val count: Int,
    val rowCount: Int
)