package com.radionov.tfcontests.utils

import com.radionov.tfcontests.data.entities.User

/**
 * @author Andrey Radionov
 */

fun User.getName(): String {
    return "$firstName $lastName $middleName"
}
