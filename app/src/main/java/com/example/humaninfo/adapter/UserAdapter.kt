package com.example.humaninfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.humaninfo.databinding.CardItemBinding
import com.example.humaninfo.fragments.HomeFragmentDirections
import com.example.humaninfo.model.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    inner class UserViewHolder( val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root)
    private val differCallback = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id ==newItem.id && oldItem.phone == newItem.phone
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
           return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            CardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = differ.currentList[position]

        holder.binding.apply {
            fioTV.text = currentUser.fio
            ageTV.text = String.format( currentUser.age.toString())
            phoneTV.text = currentUser.phone
            sexTV.text = currentUser.sex.toString()
            addressTV.text = currentUser.address
        }

        holder.itemView.setOnClickListener{
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUpdateUserFragment(currentUser))
        }
    }
}