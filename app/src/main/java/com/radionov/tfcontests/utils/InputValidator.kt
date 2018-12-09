package com.radionov.tfcontests.utils

/**
 * @author Andrey Radionov
 */
object InputValidator {

    private val rusPattern = "[А-Яа-я\\s]+".toPattern()
    private val rusPhone = "^(\\+7\\d{10})$".toPattern()
    private val fullNameSplitRegex = "\\s+".toRegex()

    fun isNameNotRus(name: String) = !rusPattern.matcher(name).matches()

    fun isPhoneNotValid(phone: String) = !rusPhone.matcher(phone).matches()

    fun isNameNotFull(name: String): Boolean {
        val nameArr = name.split(fullNameSplitRegex)
        return nameArr.size !in 2..3
    }
}
