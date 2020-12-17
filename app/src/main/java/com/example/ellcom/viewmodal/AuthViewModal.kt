package com.example.ellcom.viewmodal

import androidx.lifecycle.*
import com.example.ellcom.pojo.auth.AuthResult
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
}