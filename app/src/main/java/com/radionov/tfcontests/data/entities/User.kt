package com.radionov.tfcontests.data.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * @author Andrey Radionov
 */
@Entity(tableName = "user")
data class User(
    @PrimaryKey val id: Int,
    val email: String?,
    val birthday: String?,
    val region: String?,
    val avatar: String?,
    val university: String?,
    @SerializedName("university_graduation") val universityGraduation: Int?,
    @SerializedName("current_work") val currentWork: String?,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("middle_name") val middleName: String
)

data class UserWithStatus(
    val user: User,
    val status: String = "Ok"
)
