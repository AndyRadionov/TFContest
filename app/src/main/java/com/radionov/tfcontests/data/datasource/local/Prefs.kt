package com.radionov.tfcontests.data.datasource.local

import android.content.SharedPreferences
import com.radionov.tfcontests.utils.substringBetween

/**
 * @author Andrey Radionov
 */
class Prefs(private val prefs: SharedPreferences) {

    fun getCookie(): String = "${getAuthCookie()}; ${getCsrfCookie()}"

    fun getCookies(): Set<String>? = prefs.getStringSet(COOKIES_KEY, defaultCookies)

    fun getCsrfToken(): String? = getCsrfCookie()?.substringBetween('=', ';')

    fun getAuthCookie() = getCookies()?.firstOrNull { it.startsWith(AUTH_COOKIE) }

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

    private fun getCsrfCookie() = getCookies()?.firstOrNull { it.startsWith(CSRF_COOKIE) }

    companion object {
        private const val COOKIES_KEY = "cookies_key"
        private const val AUTH_COOKIE = "anygen"
        private const val CSRF_COOKIE = "csrf"
        private val defaultCookies = HashSet<String>()
    }
}
