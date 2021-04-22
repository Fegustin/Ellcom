package ru.steilsouth.ellcom.pojo.bills.list

data class BillsListBody(
    val contract: String,
    val dateFrom: String,
    val dateTo: String,
    val page: Int = 1,
    val pageCount: Int = 50,
)