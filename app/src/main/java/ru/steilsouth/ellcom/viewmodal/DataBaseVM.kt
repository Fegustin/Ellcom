package ru.steilsouth.ellcom.viewmodal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.steilsouth.ellcom.database.model.UserData
import ru.steilsouth.ellcom.repository.DataBaseRepository


class DataBaseVM(application: Application) : AndroidViewModel(application) {
    private val db = DataBaseRepository(application)

    fun getUserAll(): LiveData<List<UserData>> {
        val result = MutableLiveData<List<UserData>>()
        viewModelScope.launch(Dispatchers.IO) { result.postValue(db.getUserAll()) }
        return result
    }

    fun insertUser(user: UserData) {
        viewModelScope.launch(Dispatchers.IO) { db.insertUser(user) }
    }

    fun deleteUser(user: UserData) {
        viewModelScope.launch(Dispatchers.IO) { db.deleteUser(user) }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) { db.deleteAll() }
    }
}