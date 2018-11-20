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

    private val gson = Gson()

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

    fun getUser(): User =
        gson.fromJson(prefs.getString(USER_KEY, DEFAULT_LOGIN), User::class.java)

    fun setUser(user: User) {
        prefs.edit()
            .putString(USER_KEY, gson.toJson(user))
            .apply()
    }

    fun removeLogin() {
        prefs.edit()
            .remove(USER_KEY)
            .apply()
    }

    companion object {
        private const val COOKIES_KEY = "cookies_key"
        private const val USER_KEY = "user_key"
        private const val DEFAULT_LOGIN = ""
        private val defaultCookies = HashSet<String>()
    }
}
