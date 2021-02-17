package ru.steilsouth.ellcom.pojo.email.get

import com.google.gson.annotations.SerializedName
import ru.steilsouth.ellcom.pojo.email.EmailAddress

data class EmailAddressResult(
    val status: String,
    val message: String,
    val data: Data
) {
    data class Data(
        @SerializedName("return") val res: List<EmailAddress>
    )
}