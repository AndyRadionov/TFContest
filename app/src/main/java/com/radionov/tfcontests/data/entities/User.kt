package com.radionov.tfcontests.data.entities

import com.google.gson.annotations.SerializedName

/**
 * @author Andrey Radionov
 */
data class User(
    val id: Int,
    val email: String?,
    val birthday: String?,
    val description: String?,
    val region: String?,
    val avatar: String?,
    val school: String?,
    val university: String?,
    val faculty: String?,
    val grade: String?,
    val department: String?,
    val resume: String?,
    @SerializedName("school_graduation") val schoolGraduation: Int?,
    @SerializedName("university_graduation") val universityGraduation: Int?,
    @SerializedName("current_work") val currentWork: String?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("middle_name") val middleName: String?,
    @SerializedName("skype_login") val skype: String?,
    @SerializedName("phone_mobile") val phone: String?,
    @SerializedName("t_shirt_size") val shirtSize: String?
)

data class UserWithStatus(
    val user: User,
    val status: String
)
