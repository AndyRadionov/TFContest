package com.radionov.tfcontests.data.repositories

import com.radionov.tfcontests.data.datasource.local.Prefs
import com.radionov.tfcontests.data.datasource.remote.FintechApi
import com.radionov.tfcontests.data.entities.User

/**
 * @author Andrey Radionov
 */
class AuthRepository(private val prefs: Prefs, private val fintechApi: FintechApi) {

    fun login(email: String, pass: String) = fintechApi.login(email, pass)

    fun logout() = fintechApi.logout()

    fun getCookies() = prefs.getCookies()

    fun getUser() = prefs.getUser()

    fun setUser(user: User) {
        prefs.setUser(user)
    }

    fun clearAuthData() {
        prefs.removeCookies()
        prefs.removeLogin()
    }
}
