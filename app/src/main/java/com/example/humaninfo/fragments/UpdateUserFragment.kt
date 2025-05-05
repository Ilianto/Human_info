package com.example.humaninfo.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.humaninfo.MainActivity

import com.example.humaninfo.R
import com.example.humaninfo.databinding.FragmentUpdateUserBinding
import com.example.humaninfo.model.Sex
import com.example.humaninfo.model.User
import com.example.humaninfo.ui.getSelectedValue
import com.example.humaninfo.ui.setSelectedValue
import com.example.humaninfo.viewModel.UserViewModel


class UpdateUserFragment : Fragment(R.layout.fragment_update_user) {
    private var _binding: FragmentUpdateUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel

    private lateinit var currentUser: User
    private val args: UpdateUserFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = (activity as MainActivity).viewModel
        currentUser = args.user!!

        binding.apply {
            agePicker.maxValue = 120
            agePicker.minValue = 0
            val genderOptions = listOf(Sex.MALE, Sex.FEMALE)
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            etFIO.setText(currentUser.fio)
            etPhone.setText(currentUser.phone)
            addressET.setText(currentUser.address)
            agePicker.value = currentUser.age
            spinner.setSelectedValue(currentUser.sex)
            floatingActionButtonUpdate.setOnClickListener {
                val fio = binding.etFIO.text.toString()
                val sex = binding.spinner.getSelectedValue()
                val age = binding.agePicker.value
                val phone = binding.etPhone.text.toString()
                val address = binding.addressET.text.toString()
                if (fio.isNotEmpty() && sex != null && phone.isNotEmpty() && address.isNotEmpty()) {
                    val user = User(currentUser.id, fio, age, phone, sex, address)
                    userViewModel.updateUser(user)
                    Toast.makeText(context, "Saved successfully", Toast.LENGTH_SHORT).show()
                    view.findNavController().navigate(R.id.action_updateUserFragment_to_homeFragment)
                } else {
                    Toast.makeText(context, "Enter data", Toast.LENGTH_SHORT).show()
                }
            }


        }
        val toolbar: androidx.appcompat.widget.Toolbar = binding.toolbar
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)

        // Настройка меню через MenuProvider
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear() // Очищаем старое меню
                menuInflater.inflate(R.menu.menu_update_note, menu) // Подключаем новое меню
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_delete -> {
                        deleteUser()
                    }
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun deleteUser(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete note")
            setMessage("Are you sure?")
            setPositiveButton("Delete") { _, _ ->
                userViewModel.deleteUser(currentUser)
                view?.findNavController()?.navigate(R.id.action_updateUserFragment_to_homeFragment)
            }
            setNegativeButton("Cancel",null)
        }.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}