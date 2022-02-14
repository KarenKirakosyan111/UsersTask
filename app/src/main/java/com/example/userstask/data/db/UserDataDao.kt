package com.example.userstask.data.db

import androidx.room.*
import com.example.userstask.data.entity.UserData
import kotlinx.coroutines.Deferred

@Dao
interface UserDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserData)

    @Delete
    fun delete(user: UserData)

    @Query("SELECT * FROM user")
    fun getAllUsers(): List<UserData>
}