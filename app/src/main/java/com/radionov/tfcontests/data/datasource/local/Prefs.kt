package com.radionov.tfcontests.data.datasource.local

import android.content.SharedPreferences

/**
 * @author Andrey Radionov
 */
class Prefs(private val prefs: SharedPreferences) {

    fun getCookies(): String = prefs.getString(COOKIES_KEY, DEFAULT_VAL)

    fun setCookies(cookies: String) {
        prefs.edit()
            .putString(COOKIES_KEY, cookies)
            .apply()
    }

    companion object {
        private const val COOKIES_KEY = "cookies_key";
        private const val DEFAULT_VAL = ""
    }
}