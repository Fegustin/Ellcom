package ru.steilsouth.ellcom.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.steilsouth.ellcom.pojo.changepassword.ChangePasswordResult
import ru.steilsouth.ellcom.pojo.changepassword.inet.ServiceInternetResult
import ru.steilsouth.ellcom.repository.ChangePasswordRepository

class ChangePasswordVM : ViewModel() {
    private val repository: ChangePasswordRepository = ChangePasswordRepository()

    fun passwordChange(
        token: String,
        oldPassword: String,
        newPassword: String,
        isContract: Boolean,
        servId: Int
    ): LiveData<ChangePasswordResult> {
        val result = MutableLiveData<ChangePasswordResult>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(
                repository.passwordChange(
                    token,
                    oldPassword,
                    newPassword,
                    isContract,
                    servId
                )
            )
        }
        return result
    }

    fun getListServiceInternet(
        token: String
    ): LiveData<ServiceInternetResult> {
        val result = MutableLiveData<ServiceInternetResult>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.getListServiceInternet(token))
        }
        return result
    }
}