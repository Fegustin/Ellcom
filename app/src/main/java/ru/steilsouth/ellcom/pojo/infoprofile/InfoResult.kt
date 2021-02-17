package ru.steilsouth.ellcom.pojo.infoprofile

import com.google.gson.annotations.SerializedName
import ru.steilsouth.ellcom.pojo.subcontracts.SubContractsResult

data class InfoResult(
    val status: String,
    val message: String,
    val data: Data
) {
    data class Data(@SerializedName("return") val res: Result) {

        data class Result(
            val id: Int,
            @SerializedName("title") val contract_num: String,
            @SerializedName("comment") val name: String,
            val balance: Double,
            val daysCount: Int,
            val status: String,
            @SerializedName("tariffList") val rateList: List<RateList>,
            @SerializedName("subMobileContractList") val subMobileContract: List<SubContractsResult.Data.Result>
        )
    }
}