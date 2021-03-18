package ru.steilsouth.ellcom.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.steilsouth.ellcom.pojo.bills.createbills.SubMobileContract
import ru.steilsouth.ellcom.pojo.bills.list.BillsListResult
import ru.steilsouth.ellcom.repository.BillsRepository


class BillsVM : ViewModel() {
    private val repository = BillsRepository()

    fun getBillsList(
        contactNum: String,
        dateFrom: String,
        dateTo: String
    ): LiveData<BillsListResult> {
        val result = MutableLiveData<BillsListResult>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.getBillsList(contactNum, dateFrom, dateTo))
        }
        return result
    }

    fun createBills(
        token: String,
        contractNum: String,
        accountant: String,
        subMobileContractList: List<SubMobileContract>
    ): LiveData<JsonObject> {
        val result = MutableLiveData<JsonObject>()
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