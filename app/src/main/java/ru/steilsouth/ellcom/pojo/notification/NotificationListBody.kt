package ru.steilsouth.ellcom.pojo.notification

import com.google.gson.annotations.SerializedName

class NotificationListBody(token: String, notConfirm: Boolean, index: Int) {
    @SerializedName("method")
    val method: String = "contractNotificationList"

    @SerializedName("params")
    val params: Params = Params(token, notConfirm, index)

    class Params(token: String, notConfirm: Boolean, index: Int) {
        @SerializedName("token")
        val tokenRes: String = token

        @SerializedName("notConfirm")
        val notConfirm: Boolean = notConfirm

        @SerializedName("pageRow")
        val page: Page = Page(10, index)


        class Page(size: Int, index: Int) {
            @SerializedName("pageSize")
            val pageSize: Int = size

            @SerializedName("pageIndex")
            val pageIndex: Int = index
        }
    }
}