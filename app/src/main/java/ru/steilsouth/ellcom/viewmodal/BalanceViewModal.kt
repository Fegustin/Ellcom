package ru.steilsouth.ellcom.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.steilsouth.ellcom.pojo.notification.NotificationListResult
import ru.steilsouth.ellcom.repository.BalanceRepository

class BalanceViewModal : ViewModel() {
    private val repository: BalanceRepository = BalanceRepository()

    fun getBalance(
        token: String,
        data: String
    ): LiveData<NotificationListResult> {
        val result = MutableLiveData<NotificationListResult>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.getBalance(token, data))
        }
        return result
    }
}