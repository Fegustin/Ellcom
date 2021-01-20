package com.example.ellcom.pojo.contacts

import com.google.gson.annotations.SerializedName


data class MobileContactResult(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: Data
) {
    data class Data(@SerializedName("return") val res: Result) {
        data class Result(
            @SerializedName("manager") val manager: String,
            @SerializedName("managerPhoneList") val managerPhoneList: List<Phone>,
            @SerializedName("accountant") val accountant: String,
            @SerializedName("accountantPhoneList") val accountantPhoneList: List<Phone>
        )
    }
}

data class Phone(@SerializedName("phone") val phone: String)