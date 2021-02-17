package ru.steilsouth.ellcom.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.steilsouth.ellcom.pojo.auth.AuthResult
import ru.steilsouth.ellcom.pojo.forgotpassword.ForgotPasswordResult
import ru.steilsouth.ellcom.repository.AuthRepository

class AuthVM : ViewModel() {
    private val repository: AuthRepository = AuthRepository()

    fun auth(username: String, password: String, type: Boolean): LiveData<AuthResult> {
        val result = MutableLiveData<AuthResult>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.auth(username, password, type))
        }
        return result
    }

    fun forgotPassword(number: String, email: String): LiveData<ForgotPasswordResult> {
        val result = MutableLiveData<ForgotPasswordResult>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.forgotPassword(number, email))
        }
        return result
    }

    fun authOneTimePassword(token: String): LiveData<AuthResult> {
        val result = MutableLiveData<AuthResult>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.authOneTimePassword(token))
        }
        return result
    }
}