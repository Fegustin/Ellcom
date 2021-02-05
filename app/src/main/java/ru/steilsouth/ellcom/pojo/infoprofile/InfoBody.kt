package ru.steilsouth.ellcom.pojo.infoprofile

import com.google.gson.annotations.SerializedName

class InfoBody(token: String) {
    @SerializedName("method")
    val method: String = "getMobileContract"

    @SerializedName("params")
    val params: Params = Params(token)

    class Params(token: String) {
        @SerializedName("token")
        val token_res: String = token
    }
}