package com.example.userstask.data

import com.example.userstask.data.network.UserApiService
import com.example.userstask.data.network.UserDataResponse
import kotlinx.coroutines.Deferred

class UsersApiRepository {

    private val apiService = UserApiService()

    fun getDataAsync(): Deferred<UserDataResponse>  = apiService.getUsersAsync()
}