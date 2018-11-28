package com.radionov.tfcontests.utils

/**
 * @author Andrey Radionov
 */
object InputValidator {

    fun isNameValid(name: String): Boolean {
        val nameArr = name.split("\\s+".toRegex())
        if (nameArr.size < 2 || nameArr.size > 3) {
            return false
        }
        return true
    }
}