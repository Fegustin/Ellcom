package ru.steilsouth.ellcom.pojo.notification.read

import com.google.gson.annotations.SerializedName

class ReadNotificationBody(token: String, notificationsIds: String) {
    @SerializedName("method")
    private val method = "pushNotificationConfirmReceipt"

    @SerializedName("params")
    private val params = Params(token, notificationsIds)

    class Params(token: String, notificationsIds: String) {
        @SerializedName("token")
        private val tokenRes = token

        @SerializedName("notificationsIds")
        private val notificationsIdsRes = notificationsIds
    }
}