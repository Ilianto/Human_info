package com.example.humaninfo.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,

    var fio: String,

    var age: Int,

    var phone: String,

    var sex: Sex,

    var address: String
) : Parcelable
