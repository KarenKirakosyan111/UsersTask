package com.example.userstask.data

import com.example.userstask.data.db.UserDataDao
import com.example.userstask.data.entity.UserData
import com.example.userstask.data.network.UserDataResponse
import kotlinx.coroutines.Deferred

class UsersDbRepository {
    fun getData(userDataDao: UserDataDao): List<UserData> = userDataDao.getAllUsers()
}