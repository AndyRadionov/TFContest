package com.radionov.tfcontests.data.datasource.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.radionov.tfcontests.data.entities.User
import es.dmoral.toasty.Toasty
import kotlin.collections.HashSet

/**
 * @author Andrey Radionov
 */
class Prefs(private val prefs: SharedPreferences) {

    fun getCookies(): Set<String>? = prefs.getStringSet(COOKIES_KEY, defaultCookies)

    fun getAuthCookie() = getCookies()?.firstOrNull { it.startsWith(AUTH_COOKIE) }

    fun getCsrfCookie() = getCookies()?.firstOrNull { it.startsWith(CSRF_COOKIE) }

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
        private const val COOKIES_KEY = "cookies_key"
        private const val AUTH_COOKIE = "anygen"
        private const val CSRF_COOKIE = "csrf"
        private val defaultCookies = HashSet<String>()
    }
}
