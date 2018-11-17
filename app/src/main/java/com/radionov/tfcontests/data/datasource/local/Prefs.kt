package com.radionov.tfcontests.data.datasource.local

import android.content.SharedPreferences
import kotlin.collections.HashSet

/**
 * @author Andrey Radionov
 */
class Prefs(private val prefs: SharedPreferences) {

    fun getCookies(): Set<String>? = prefs.getStringSet(COOKIES_KEY, defaultCookies)

    fun setCookies(cookies: Set<String>) {
        prefs.edit()
            .putStringSet(COOKIES_KEY, cookies)
            .apply()
    }

    fun removeCookies() {
        prefs.edit()
            .remove(COOKIES_KEY)
            .apply()
    }

    companion object {
        private const val COOKIES_KEY = "cookies_key";
        private val defaultCookies = HashSet<String>()
    }
}