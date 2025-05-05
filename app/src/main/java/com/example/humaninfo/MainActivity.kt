package com.example.humaninfo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.humaninfo.databinding.ActivityMainBinding
import com.example.humaninfo.database.UserDatabase
import com.example.humaninfo.repository.UserRepositoryDB
import com.example.humaninfo.viewModel.UserViewModel
import com.example.humaninfo.viewModel.UserViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
    }

    private fun setUpViewModel() {
        val userRepositoryDB = UserRepositoryDB(UserDatabase(this).userDAO)
        val viewModelFactory = UserViewModelFactory(application, userRepositoryDB)
        viewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
    }

}