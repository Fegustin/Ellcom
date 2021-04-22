package ru.steilsouth.ellcom.pojo.bills.list

data class BillsListResult(
    val data: List<Data>,
    val message: String,
    val status: String
)

data class Data(
    val acc_date: String,
    val acc_id: String,
    val acc_number: String,
    val acc_records: List<AccRecord>
)

data class AccRecord(
    val service: String,
    val sum: Float
)