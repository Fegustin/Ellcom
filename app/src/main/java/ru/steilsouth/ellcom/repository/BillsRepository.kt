package ru.steilsouth.ellcom.repository

import android.util.Log
import ru.steilsouth.ellcom.api.ApiUtils
import ru.steilsouth.ellcom.pojo.TotalReturnValue
import ru.steilsouth.ellcom.pojo.bills.createbills.CreateBillsBody
import ru.steilsouth.ellcom.pojo.bills.createbills.SubMobileContract
import ru.steilsouth.ellcom.pojo.bills.list.BillsListBody
import ru.steilsouth.ellcom.pojo.bills.list.BillsListResult

class BillsRepository {
    private val tag = "Error: class -> BillsRepository: "

    suspend fun getBillsList(
        token: String,
        contactNum: String,
        dateFrom: String,
        dateTo: String
    ): BillsListResult? {
        return try {
            ApiUtils.apiCms.getBillsList(BillsListBody(token, contactNum, dateFrom, dateTo))
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
    ): TotalReturnValue? {
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