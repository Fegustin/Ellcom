package ru.steilsouth.ellcom.pojo.subcontracts

import com.google.gson.annotations.SerializedName
import ru.steilsouth.ellcom.pojo.infoprofile.RateList

data class SubContractsResult(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: Data
) {
    data class Data(@SerializedName("return") val res: List<Result>) {

        data class Result(
            @SerializedName("id") val id: Int,
            @SerializedName("title") val title: String,
            @SerializedName("comment") val comment: String,
            @SerializedName("status") val status: String,
            @SerializedName("balance") val balance: Double,
            @SerializedName("tariffList") val rateList: List<RateList>,
        )
    }
}