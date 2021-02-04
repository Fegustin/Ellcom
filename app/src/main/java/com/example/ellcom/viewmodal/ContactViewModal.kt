package com.example.ellcom.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ellcom.pojo.contacts.MobileContactResult
import com.example.ellcom.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ContactViewModal : ViewModel() {
    private val repository: ContactRepository = ContactRepository()

    fun getMobileContact(token: String): LiveData<MobileContactResult> {
        val result = MutableLiveData<MobileContactResult>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.getMobileContact(token))
        }
        return result
    }
}