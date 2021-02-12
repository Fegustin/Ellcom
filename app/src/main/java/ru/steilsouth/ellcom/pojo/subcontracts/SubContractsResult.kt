package ru.steilsouth.ellcom.pojo.subcontracts

import com.google.gson.annotations.SerializedName
import ru.steilsouth.ellcom.pojo.infoprofile.RateList

data class SubContractsResult(
    val status: String,
    val message: String,
    val data: Data
) {
    data class Data(@SerializedName("return") val res: List<Result>) {

        data class Result(
            val id: Int,
            val title: String,
            val comment: String,
            val status: String,
            val token: String,
            val balance: Double,
            @SerializedName("tariffList") val rateList: List<RateList>
        )
    }
}