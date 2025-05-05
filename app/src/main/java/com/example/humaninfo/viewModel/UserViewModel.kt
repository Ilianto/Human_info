package com.example.humaninfo.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.humaninfo.model.User
import com.example.humaninfo.repository.UserRepositoryDB
import kotlinx.coroutines.launch

class UserViewModel(
    app: Application,
    private val userRepositoryDB: UserRepositoryDB
) : AndroidViewModel(app) {
    fun addUser(user: User) = viewModelScope.launch { userRepositoryDB.insert(user) }
    fun updateUser(user: User) = viewModelScope.launch { userRepositoryDB.update(user) }
    fun deleteUser(user: User) = viewModelScope.launch { userRepositoryDB.delete(user) }

    fun getAllUsers() = userRepositoryDB.users
    fun searchUser(query: String) = userRepositoryDB.searchUser(query)
}