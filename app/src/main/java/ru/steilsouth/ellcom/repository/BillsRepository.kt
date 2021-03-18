package ru.steilsouth.ellcom.repository

import android.util.Log
import com.google.gson.JsonObject
import ru.steilsouth.ellcom.api.ApiUtils
import ru.steilsouth.ellcom.pojo.bills.createbills.CreateBillsBody
import ru.steilsouth.ellcom.pojo.bills.createbills.SubMobileContract
import ru.steilsouth.ellcom.pojo.bills.list.BillsListBody
import ru.steilsouth.ellcom.pojo.bills.list.BillsListResult

class BillsRepository {
    private val tag = "Error: class -> BillsRepository: "

    suspend fun getBillsList(
        contactNum: String,
        dateFrom: String,
        dateTo: String
    ): BillsListResult? {
        return try {
            ApiUtils.apiCms.getBillsList(BillsListBody(contactNum, dateFrom, dateTo))
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }

    suspend fun createBills(
        token: String,
        contractNum: String,
        accountant: String,
        subMobileContractList: List<SubMobileContract>
    ): JsonObject? {
        return try {
            ApiUtils.apiCms.createBills(
                CreateBillsBody(
                    token,
                    contractNum,
                    accountant,
                    subMobileContractList
                )
            )
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }
}