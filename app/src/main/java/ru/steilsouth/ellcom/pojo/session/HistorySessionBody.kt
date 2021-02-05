package ru.steilsouth.ellcom.pojo.session

import com.google.gson.annotations.SerializedName

class HistorySessionBody(token: String, servId: Int, dateFrom: String, dateTo: String) {
    @SerializedName("method")
    val method: String = "getInetSessionLogList"

    @SerializedName("params")
    val params: Params = Params(token, servId, dateFrom, dateTo)

    class Params(token: String, servId: Int, dateFrom: String, dateTo: String) {
        @SerializedName("token")
        val tokenRes = token

        @SerializedName("servId")
        val servIdRes = servId

        @SerializedName("dateFrom")
        val dateFromRes = dateFrom

        @SerializedName("dateTo")
        val dateToRes = dateTo
    }
}