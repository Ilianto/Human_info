package com.example.humaninfo.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.humaninfo.repository.UserRepositoryDB

class UserViewModelFactory(
    private val app: Application, private val userRepositoryDB: UserRepositoryDB
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(app, userRepositoryDB) as T

    }
}