package com.example.ellcom.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ellcom.pojo.infoprofile.InfoResult
import com.example.ellcom.pojo.notification.NotificationListBody
import com.example.ellcom.pojo.notification.NotificationListResult
import com.example.ellcom.pojo.subcontracts.SubContractsResult
import com.example.ellcom.repository.MainAndSubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainAndSubViewModal : ViewModel() {
    private val repository = MainAndSubRepository()

    fun infoProfile(token: String): LiveData<InfoResult> {
        val result = MutableLiveData<InfoResult>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.infoProfile(token))
        }
        return result
    }

    fun getSubContractsList(token: String): LiveData<SubContractsResult> {
        val result = MutableLiveData<SubContractsResult>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.getSubContractsList(token))
        }
        return result
    }

    fun getNotificationList(token: String, notConfirm: Boolean, page: Int): LiveData<NotificationListResult> {
        val result = MutableLiveData<NotificationListResult>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.getNotificationList(token, notConfirm, page))
        }
        return result
    }
}