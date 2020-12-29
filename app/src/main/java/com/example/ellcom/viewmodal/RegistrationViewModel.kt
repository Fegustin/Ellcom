package com.example.ellcom.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ellcom.repository.RegistrationRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class RegistrationViewModel : ViewModel() {
    private val repository: RegistrationRepository = RegistrationRepository()

    fun registration(
        name: String,
        nameCompany: String,
        phone: String,
        address: String,
        email: String
    ): LiveData<Response<JsonObject>> {
        val result = MutableLiveData<Response<JsonObject>>()
        viewModelScope.launch {
            result.postValue(repository.registration(name, nameCompany, phone, address, email))
        }
        return result
    }
}