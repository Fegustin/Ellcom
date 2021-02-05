package ru.steilsouth.ellcom.pojo.changepassword.inet

import com.google.gson.annotations.SerializedName

data class ServiceInternetResult(
    val status: String,
    val message: String,
    val data: Data,
) {
    data class Data(@SerializedName("return") val res: List<Result>) {
        data class Result(
            val id: Int,
            val title: String,
            val login: String
        )
    }
}