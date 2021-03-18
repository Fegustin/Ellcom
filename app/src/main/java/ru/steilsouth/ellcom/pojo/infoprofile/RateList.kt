package ru.steilsouth.ellcom.pojo.infoprofile

data class RateList(
    val tariffTitle: String,
    val tariffPrice: String,
    val dateFrom: String,
    val dateTo: String,
    val status: String
)