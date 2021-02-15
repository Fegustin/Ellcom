package ru.steilsouth.ellcom.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.steilsouth.ellcom.pojo.changepassword.ChangePasswordResult
import ru.steilsouth.ellcom.repository.ReallocationFundsRepository
import java.math.BigDecimal


class ReallocationFundsViewModal : ViewModel() {
    private val repository = ReallocationFundsRepository()

    fun reallocationOfFunds(token: String, id: Int, amount: BigDecimal): LiveData<ChangePasswordResult> {
        val result = MutableLiveData<ChangePasswordResult>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.reallocationOfFunds(token, id, amount))
        }
        return result
    }
}