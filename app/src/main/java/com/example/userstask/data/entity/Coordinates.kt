package com.example.userstask.data.entity


import com.google.gson.annotations.SerializedName

data class Coordinates(
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String
)