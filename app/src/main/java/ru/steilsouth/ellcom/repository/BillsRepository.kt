package ru.steilsouth.ellcom.repository

import android.util.Log
import retrofit2.Response
import ru.steilsouth.ellcom.api.ApiUtils
import ru.steilsouth.ellcom.pojo.bills.createbills.CreateBillsBody
import ru.steilsouth.ellcom.pojo.bills.createbills.CreateBillsResult
import ru.steilsouth.ellcom.pojo.bills.createbills.SubMobileContract
import ru.steilsouth.ellcom.pojo.bills.list.BillsListBody
import ru.steilsouth.ellcom.pojo.bills.list.BillsListResult
import ru.steilsouth.ellcom.utils.enam.Token

class BillsRepository {
    private val tag = "Error: class -> BillsRepository: "

    suspend fun getBillsList(
        contact: String,
        dateFrom: String,
        dateTo: String
    ): Response<BillsListResult>? {
        return try {
            ApiUtils.apiCms.getBillsList(
                BillsListBody(contact, dateFrom, dateTo),
                Token.CmsEllco.token
            )
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
    ): Response<CreateBillsResult>? {
        return try {
            ApiUtils.apiCms.createBills(
                CreateBillsBody(
                    token,
                    contractNum,
                    accountant,
                    subMobileContractList
                ),
                Token.CmsEllco.token
            )
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }
}