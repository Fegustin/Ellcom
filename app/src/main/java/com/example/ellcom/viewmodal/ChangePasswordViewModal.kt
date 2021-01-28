package com.example.ellcom.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ellcom.pojo.changepassword.ChangePasswordResult
import com.example.ellcom.pojo.changepassword.inet.ServiceInternetResult
import com.example.ellcom.repository.ChangePasswordRepository
import kotlinx.coroutines.launch

class ChangePasswordViewModal : ViewModel() {
    private val repository: ChangePasswordRepository = ChangePasswordRepository()

    fun passwordChange(
        token: String,
        oldPassword: String,
        newPassword: String,
        isContract: Boolean,
        servId: Int
    ): LiveData<ChangePasswordResult> {
        val result = MutableLiveData<ChangePasswordResult>()
        viewModelScope.launch {
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
        viewModelScope.launch {
            result.postValue(repository.getListServiceInternet(token))
        }
        return result
    }
}