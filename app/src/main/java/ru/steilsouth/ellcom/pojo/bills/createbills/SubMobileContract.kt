package ru.steilsouth.ellcom.pojo.bills.createbills

data class SubMobileContract(
    val content: String,
    val contract: String,
    val quantity: Int,
    val sum: Int,
    val type: String,
    val unit: String
)