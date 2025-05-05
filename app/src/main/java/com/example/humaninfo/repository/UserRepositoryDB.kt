package com.example.humaninfo.repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.humaninfo.database.UserDAO
import com.example.humaninfo.model.User

class UserRepositoryDB(private val dao: UserDAO) {

    val users = dao.getAllUsersInDB()

    suspend fun insert(user: User): Long = dao.insertUser(user)


    suspend fun delete(user: User) = dao.deleteUser(user)


    suspend fun update(user: User) = dao.updateUser(user)


    suspend fun deleteAll() = dao.deleteAll()

    fun searchUser(query: String):LiveData<List<User>> = dao.searchUser(query)
}