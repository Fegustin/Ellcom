package ru.steilsouth.ellcom.repository

import android.app.Application
import android.util.Log
import ru.steilsouth.ellcom.database.MainDataBase
import ru.steilsouth.ellcom.database.model.UserData


class DataBaseRepository(application: Application) {
    private val tag = "Error: class -> DataBaseRepository: "
    private val db = MainDataBase.getInstance(application)

    suspend fun getUserAll(): List<UserData>? {
        return try {
            db.userDao().getAll()
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }

    suspend fun insertUser(user: UserData) {
        try {
            db.userDao().insert(user)
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
        }
    }

    suspend fun getUser(token: String): UserData? {
        return try {
            db.userDao().findUser(token)
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
            null
        }
    }

    suspend fun deleteUser(user: UserData) {
        try {
            db.userDao().delete(user)
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
        }
    }

    suspend fun deleteAll() {
        try {
            db.userDao().deleteAll()
        } catch (e: Throwable) {
            Log.e(tag, e.localizedMessage)
        }
    }
}