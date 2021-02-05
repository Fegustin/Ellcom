package ru.steilsouth.ellcom.pojo.infoprofile

import com.google.gson.annotations.SerializedName
import ru.steilsouth.ellcom.pojo.subcontracts.SubContractsResult

class InfoResult(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: Data
) {
    data class Data(@SerializedName("return") val res: Result) {

        data class Result(
            @SerializedName("id") val id: Int,
            @SerializedName("title") val contract_num: String,
            @SerializedName("comment") val name: String,
            @SerializedName("balance") val balance: Double,
            @SerializedName("daysCount") val daysCount: Int,
            @SerializedName("status") val status: String,
            @SerializedName("tariffList") val rateList: List<RateList>,
            @SerializedName("subMobileContractList") val subMobileContract: List<SubContractsResult.Data.Result>
        )
    }
}