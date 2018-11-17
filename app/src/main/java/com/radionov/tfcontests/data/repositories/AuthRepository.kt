package com.radionov.tfcontests.data.repositories

import com.radionov.tfcontests.data.datasource.local.Prefs
import com.radionov.tfcontests.data.datasource.remote.FintechApi

/**
 * @author Andrey Radionov
 */
class AuthRepository(private val prefs: Prefs, private val fintechApi: FintechApi) {

    fun login(email: String, pass: String) = fintechApi.login(email, pass)

    fun getCookies() = prefs.getCookies()

    fun setCookies(cookies: Set<String>) {
        prefs.setCookies(cookies)
    }

    fun removeCookies() {
        prefs.removeCookies()
    }
}
