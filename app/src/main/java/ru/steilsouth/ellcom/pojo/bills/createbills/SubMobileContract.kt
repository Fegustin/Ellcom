package ru.steilsouth.ellcom.pojo.bills.createbills

data class SubMobileContract(
    val contract: String,
    val content: String,
    val quantity: Int,
    val sum: Int,
    val type: String,
    val unit: String = "мес"
)