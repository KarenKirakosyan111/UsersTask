package com.example.userstask.data.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class UserData(
    @SerializedName("cell")
    val cell: String?,
    @Embedded(prefix = "dob_")
    val dob: Dob?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("gender")
    val gender: String?,
    @Embedded(prefix = "id_")
    val apiId: Id?,
    @Embedded(prefix = "location_")
    val location: Location?,
    @Embedded(prefix = "login_")
    val login: Login?,
    @Embedded(prefix = "name_")
    val name: Name?,
    @SerializedName("nat")
    val nat: String?,
    @SerializedName("phone")
    val phone: String?,
    @Embedded(prefix = "picture_")
    val picture: Picture?,
    @Embedded(prefix = "registered_")
    val registered: Registered?,
    @PrimaryKey(autoGenerate = true)
    val idd: Int
)
