package ru.steilsouth.ellcom.pojo.bills.createbills

import com.google.gson.annotations.SerializedName

data class CreateBillsBody(
    val token: String,
    val supMobileContract: String,
    @SerializedName("title") val accountant: String,
    val subMobileContractList: List<SubMobileContract>
)