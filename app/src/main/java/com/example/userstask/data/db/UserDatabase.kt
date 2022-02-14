package com.example.userstask.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.userstask.data.entity.UserData

@Database(entities = [UserData::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDataDao(): UserDataDao

    companion object {
        @Volatile private var instance: UserDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "users.db")
                .build()
    }
}