package com.example.ellcom.viewmodal

import androidx.lifecycle.*
import com.example.ellcom.pojo.auth.AuthResult
import com.example.ellcom.pojo.forgotpassword.ForgotPasswordResult
import com.example.ellcom.pojo.infoprofile.InfoResult
import com.example.ellcom.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModal : ViewModel() {
    private val repository: AuthRepository = AuthRepository()

    fun auth(username: String, password: String, type: Boolean): LiveData<AuthResult> {
        val result = MutableLiveData<AuthResult>()
        viewModelScope.launch {
            result.postValue(repository.auth(username, password, type))
        }
        return result
    }

    fun forgotPassword(number: String, email: String): LiveData<ForgotPasswordResult> {
        val result = MutableLiveData<ForgotPasswordResult>()
        viewModelScope.launch {
            result.postValue(repository.forgotPassword(number, email))
        }
        return result
    }

    fun authOneTimePassword(token: String): LiveData<AuthResult> {
        val result = MutableLiveData<AuthResult>()
        viewModelScope.launch {
            result.postValue(repository.authOneTimePassword(token))
        }
        return result
    }

    fun infoProfile(token: String): LiveData<InfoResult> {
        val result = MutableLiveData<InfoResult>()
        viewModelScope.launch {
            result.postValue(repository.infoProfile(token))
        }
        return result
    }
}