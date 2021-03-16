package ru.steilsouth.ellcom.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UserData(
    @PrimaryKey val token: String,
    val number: String,
    val name: String,
)