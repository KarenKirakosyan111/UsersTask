package com.example.userstask.data.entity


import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("city")
    val city: String,
    @Embedded(prefix = "coordinates_")
    val coordinates: Coordinates,
    @SerializedName("country")
    val country: String,
    @SerializedName("postcode")
    val postcode: String,
    @SerializedName("state")
    val state: String,
    @Embedded(prefix = "street_")
    val street: Street,
    @Embedded(prefix = "timezone_")
    val timezone: Timezone
)