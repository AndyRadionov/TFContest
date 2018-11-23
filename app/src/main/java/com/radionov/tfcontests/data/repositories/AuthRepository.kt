package com.radionov.tfcontests.data.repositories

import com.radionov.tfcontests.data.datasource.local.Prefs
import com.radionov.tfcontests.data.datasource.remote.FintechApi
import com.radionov.tfcontests.data.entities.User
import io.reactivex.Completable

/**
 * @author Andrey Radionov
 */
class AuthRepository(private val prefs: Prefs, private val fintechApi: FintechApi) {

    fun login(email: String, pass: String) = fintechApi.login(email, pass)

    fun restorePass(email: String) = fintechApi.restorePass(email)

    fun logout(): Completable {
        val cookies = prefs.getCookies()
        val token = cookies?.firstOrNull { it.startsWith("csrf") }
        return fintechApi.logout(token)
    }

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
