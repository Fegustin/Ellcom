package ru.steilsouth.ellcom.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.steilsouth.ellcom.repository.RegistrationRepository

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
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.registration(name, nameCompany, phone, address, email))
        }
        return result
    }
}