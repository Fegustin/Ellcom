package ru.steilsouth.ellcom.pojo.notification.delete

import com.google.gson.annotations.SerializedName

class DeleteNotificationBody(token: String, notificationsId: Int) {
    @SerializedName("method")
    private val method = "deleteNotification"

    @SerializedName("params")
    private val params = Params(token, notificationsId)

    class Params(token: String, notificationsId: Int) {
        @SerializedName("token")
        private val tokenRes = token

        @SerializedName("notificationsIds")
        private val notificationsIdsRes = notificationsId
    }
}