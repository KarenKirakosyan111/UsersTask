package com.example.userstask.data.network


import com.example.userstask.data.entity.Info
import com.example.userstask.data.entity.UserData
import com.google.gson.annotations.SerializedName

data class UserDataResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val userData: List<UserData>
)