package com.radionov.tfcontests.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Andrey Radionov
 */
private val BIRTHDAY_FORMAT = SimpleDateFormat("dd.MM.yyyy", Locale.ROOT)


fun formatBirthday(birthday: Date): String? {
    return BIRTHDAY_FORMAT.format(birthday)
}