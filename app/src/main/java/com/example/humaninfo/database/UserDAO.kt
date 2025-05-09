package com.example.humaninfo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.humaninfo.model.User

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(user: User):Long

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user")
    suspend fun deleteAll()

    @Query("SELECT * FROM user")
    fun getAllUsersInDB():LiveData<List<User>>

    @Query("SELECT * FROM user WHERE fio LIKE '%' || :query || '%' OR phone LIKE '%' || :query || '%' OR address LIKE '%' || :query || '%'")
    fun searchUser(query: String?): LiveData<List<User>>

}