package ru.steilsouth.ellcom.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.steilsouth.ellcom.pojo.bills.createbills.CreateBillsResult
import ru.steilsouth.ellcom.pojo.bills.createbills.SubMobileContract
import ru.steilsouth.ellcom.pojo.bills.list.BillsListResult
import ru.steilsouth.ellcom.repository.BillsRepository


class BillsVM : ViewModel() {
    private val repository = BillsRepository()

    fun getBillsList(
        contact: String,
        dateFrom: String,
        dateTo: String
    ): LiveData<Response<BillsListResult>> {
        val result = MutableLiveData<Response<BillsListResult>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.getBillsList(contact, dateFrom, dateTo))
        }
        return result
    }

    fun createBills(
        token: String,
        contractNum: String,
        accountant: String,
        subMobileContractList: List<SubMobileContract>
    ): LiveData<Response<CreateBillsResult>> {
        val result = MutableLiveData<Response<CreateBillsResult>>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(
                repository.createBills(
                    token,
                    contractNum,
                    accountant,
                    subMobileContractList
                )
            )
        }
        return result
    }
}