package ru.steilsouth.ellcom.database

import androidx.room.*
import ru.steilsouth.ellcom.database.model.UserData


@Dao
interface UserDAO {

    @Query("SELECT * FROM UserData ORDER BY token DESC")
    fun getAll(): List<UserData>

    @Query("SELECT * FROM UserData WHERE token == :token")
    fun findUser(token: String): UserData

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserData)

    @Delete
    fun delete(user: UserData)

    @Query("DELETE FROM UserData")
    fun deleteAll()
}