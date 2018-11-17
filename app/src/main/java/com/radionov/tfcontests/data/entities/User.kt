package com.radionov.tfcontests.data.entities

import com.google.gson.annotations.SerializedName

/**
 * @author Andrey Radionov
 */
data class User(val email: String?,
                @SerializedName("first_name")
                 val firstName: String?,
                @SerializedName("second_name")
                 val lastName: String?)