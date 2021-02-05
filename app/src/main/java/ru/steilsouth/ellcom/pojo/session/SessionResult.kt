package ru.steilsouth.ellcom.pojo.session

import com.google.gson.annotations.SerializedName


data class SessionResult(
    val status: String,
    val message: String,
    val data: Data
) {
    data class Data(
        @SerializedName("return") val res: List<Return>,
    ) {
        data class Return(
            val id: Int,
            val connectionId: Int,
            val username: String,
            val callingStationId: String,
            val ipAddress: String,
            val sessionStart: String,
            val sessionStop: String,
            val connectionStart: String,
            val incomingTraffic: Int,
            val outgoingTraffic: Int
        )
    }
}