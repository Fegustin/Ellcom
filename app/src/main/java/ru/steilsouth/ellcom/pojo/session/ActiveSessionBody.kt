package ru.steilsouth.ellcom.pojo.session

import com.google.gson.annotations.SerializedName

class ActiveSessionBody(token: String, servId: Int) {
    @SerializedName("method")
    val method: String = "getInetSessionAliveList"

    @SerializedName("params")
    val params = Params(token, servId)

    class Params(token: String, servId: Int) {
        @SerializedName("token")
        val tokenRes = token

        @SerializedName("servId")
        val servIdRes = servId
    }
}