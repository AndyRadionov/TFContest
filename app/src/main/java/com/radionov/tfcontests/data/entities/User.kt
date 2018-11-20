package com.radionov.tfcontests.data.entities

import com.google.gson.annotations.SerializedName

/**
 * @author Andrey Radionov
 */
data class User(
    val email: String?,
    val birthday: String?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?
)