package com.example.humaninfo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.humaninfo.MainActivity

import com.example.humaninfo.R
import com.example.humaninfo.databinding.FragmentHomeBinding
import com.example.humaninfo.databinding.FragmentNewUserBinding
import com.example.humaninfo.model.Sex
import com.example.humaninfo.model.User
import com.example.humaninfo.ui.getSelectedValue
import com.example.humaninfo.viewModel.UserViewModel

class NewUserFragment : Fragment(R.layout.fragment_new_user) {
    private var _binding: FragmentNewUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel

    private lateinit var mView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = (activity as MainActivity).viewModel
        mView = view

        val toolbar: androidx.appcompat.widget.Toolbar = binding.toolbar
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        binding.apply {
            agePicker.maxValue = 120
            agePicker.minValue = 0
            val genderOptions = listOf(Sex.MALE, Sex.FEMALE)
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        // Настройка меню через MenuProvider
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear() // Очищаем старое меню
                menuInflater.inflate(R.menu.menu_new_note, menu) // Подключаем новое меню
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_save -> {
                        saveUser(mView)
                    }
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun saveUser(view: View) {
        val fio = binding.etFIO.text.toString()
        val sex = binding.spinner.getSelectedValue()
        val age = binding.agePicker.value
        val phone = binding.etPhone.text.toString()
        val address = binding.addressET.text.toString()
        if (fio.isNotEmpty() && sex != null && phone.isNotEmpty() && address.isNotEmpty()) {
            val user = User(0, fio, age, phone, sex, address)
            userViewModel.addUser(user)
            Toast.makeText(mView.context, "Saved successfully", Toast.LENGTH_SHORT).show()
            view.findNavController().navigate(R.id.action_newUserFragment_to_homeFragment)
        } else {
            Toast.makeText(mView.context, "Enter data", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}