package ru.steilsouth.ellcom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.steilsouth.ellcom.database.dao.UserDAO
import ru.steilsouth.ellcom.database.model.UserData


@Database(
    entities = [UserData::class],
    version = 1,
    exportSchema = false
)
abstract class MainDataBase : RoomDatabase() {
    abstract fun userDao(): UserDAO

    companion object {
        private var INSTANCE: MainDataBase? = null

        fun getInstance(context: Context): MainDataBase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MainDataBase::class.java,
                    "mainDB"
                )
                    .build()
            }

            return INSTANCE as MainDataBase
        }
    }
}