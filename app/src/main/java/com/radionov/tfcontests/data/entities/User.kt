package com.radionov.tfcontests.data.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * @author Andrey Radionov
 */
@Parcelize
@Entity(tableName = "user")
data class User(
    @SerializedName("id") @PrimaryKey val id: Int,
    @SerializedName("email") var email: String?,
    @SerializedName("birthday") var birthday: String?,
    @SerializedName("region") var region: String?,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("school") var school: String?,
    @SerializedName("university") var university: String?,
    @SerializedName("faculty") var faculty: String?,
    @SerializedName("department") var department: String?,
    @SerializedName("resume") var resume: String?,
    @SerializedName("school_graduation") var schoolGraduation: Int?,
    @SerializedName("university_graduation") var universityGraduation: Int?,
    @SerializedName("current_work") var currentWork: String?,
    @SerializedName("phone_mobile") var phone: String?,
    @SerializedName("t_shirt_size") var shirtSize: String?,
    @SerializedName("first_name") var firstName: String?,
    @SerializedName("last_name") var lastName: String?,
    @SerializedName("middle_name") var middleName: String?

) : Parcelable

data class UserWithStatus(
    @SerializedName("user") val user: User,
    @SerializedName("status") val status: String = "Ok"
)
