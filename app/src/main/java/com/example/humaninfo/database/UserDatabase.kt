package com.example.humaninfo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.humaninfo.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract val userDAO: UserDAO

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE?:
            synchronized(LOCK){
                INSTANCE?:
                createDatabase(context)
            }

        private fun createDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,UserDatabase::class.java,"user_db").build()
    }
}