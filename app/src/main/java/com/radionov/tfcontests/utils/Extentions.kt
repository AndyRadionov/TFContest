package com.radionov.tfcontests.utils

import com.radionov.tfcontests.data.entities.User

/**
 * @author Andrey Radionov
 */

fun User.getName(): String {
    return "$lastName $firstName $middleName"
}

fun User.setName(name: String) {
    val nameArr = name.split("\\s+".toRegex())
    this.lastName = nameArr[0]
    this.firstName = nameArr[1]
    if (nameArr.size == 3) {
        this.middleName = nameArr[2]
    }
}
