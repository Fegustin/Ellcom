package com.example.ellcom.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ellcom.pojo.session.SessionResult
import com.example.ellcom.repository.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SessionViewModal : ViewModel() {
    private val repository: SessionRepository = SessionRepository()

    fun getActiveSession(
        token: String,
        servId: Int
    ): LiveData<SessionResult> {
        val result = MutableLiveData<SessionResult>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.getActiveSession(token, servId))
        }
        return result
    }

    fun getHistorySession(
        token: String,
        servId: Int,
        dateFrom: String,
        dateTo: String
    ): LiveData<SessionResult> {
        val result = MutableLiveData<SessionResult>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.getHistorySession(token, servId, dateFrom, dateTo))
        }
        return result
    }
}