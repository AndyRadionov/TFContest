package com.radionov.tfcontests.data.repositories

import com.radionov.tfcontests.data.datasource.local.Prefs
import com.radionov.tfcontests.data.datasource.remote.FintechApi

/**
 * @author Andrey Radionov
 */
class AuthRepository(
    private val prefs: Prefs,
    private val fintechApi: FintechApi
) {

    fun login(email: String, pass: String) = fintechApi.login(email, pass)

    fun restorePass(email: String) = fintechApi.restorePass(email)

    fun logout() = fintechApi.logout()

    fun getAuthCookie() = prefs.getAuthCookie()

    fun removeCookies() = prefs.removeCookies()
}
