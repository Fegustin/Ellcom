package ru.steilsouth.ellcom.pojo.bills.list

data class BillsListBody(
    val token: String,
    val сontract: String,
    val dateFrom: String,
    val dateTo: String
)