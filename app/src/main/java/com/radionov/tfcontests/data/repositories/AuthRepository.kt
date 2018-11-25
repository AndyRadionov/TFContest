package com.radionov.tfcontests.data.repositories

import com.radionov.tfcontests.data.datasource.local.Prefs
import com.radionov.tfcontests.data.datasource.local.db.UserDao
import com.radionov.tfcontests.data.datasource.remote.FintechApi
import com.radionov.tfcontests.data.entities.User
import io.reactivex.Completable

/**
 * @author Andrey Radionov
 */
class AuthRepository(
    private val prefs: Prefs,
    private val fintechApi: FintechApi
) {

    fun login(email: String, pass: String) = fintechApi.login(email, pass)

    fun restorePass(email: String) = fintechApi.restorePass(email)

    fun logout(): Completable {
        val csrfCookie = prefs.getCsrfCookie()
        return fintechApi.logout(csrfCookie)
    }

    fun getAuthCookie() = prefs.getAuthCookie()

    fun removeCookies() {
        prefs.removeCookies()
    }
}
