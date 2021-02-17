package ru.steilsouth.ellcom.pojo.email.get

import com.google.gson.annotations.SerializedName

class GetEmailListBody(token: String) {
    @SerializedName("method")
    private val method = "getEmailContactList"

    @SerializedName("params")
    private val params = Params(token)

    class Params(token: String) {
        @SerializedName("token")
        private val tokenRes = token
    }
}