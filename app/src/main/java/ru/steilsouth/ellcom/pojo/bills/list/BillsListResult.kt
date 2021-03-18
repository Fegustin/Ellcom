package ru.steilsouth.ellcom.pojo.bills.list

data class BillsListResult(
    val data: List<Any>,
    val message: String,
    val status: String
)