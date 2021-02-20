package ru.steilsouth.ellcom.pojo.notification.subscribe

import com.google.gson.annotations.SerializedName

class SubscribeNotificationsBody(token: String, mobileToken: String, subscribe: Boolean) {
    @SerializedName("method")
    private val method = "subscribeToPushNotifications"

    @SerializedName("params")
    private val params = Params(token, mobileToken, subscribe)

    class Params(token: String, mobileToken: String, subscribe: Boolean) {
        @SerializedName("token")
        private val tokenRes = token

        @SerializedName("mobileToken")
        private val mobileTokenRes = mobileToken

        @SerializedName("type")
        private val type = 2

        @SerializedName("subscribe")
        private val subscribeRes = subscribe
    }
}