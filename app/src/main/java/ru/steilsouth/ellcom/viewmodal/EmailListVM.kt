package ru.steilsouth.ellcom.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.steilsouth.ellcom.pojo.changepassword.ChangePasswordResult
import ru.steilsouth.ellcom.pojo.email.EmailAddress
import ru.steilsouth.ellcom.pojo.email.get.EmailAddressResult
import ru.steilsouth.ellcom.repository.EmailListRepository

class EmailListVM : ViewModel() {
    private val repository = EmailListRepository()

    fun getEmailList(token: String): LiveData<EmailAddressResult> {
        val result = MutableLiveData<EmailAddressResult>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.getEmailList(token))
        }
        return result
    }

    fun updateEmail(
        token: String,
        email: MutableList<EmailAddress>
    ): LiveData<ChangePasswordResult> {
        val result = MutableLiveData<ChangePasswordResult>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.updateEmailList(token, email))
        }
        return result
    }
}