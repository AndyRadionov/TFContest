package com.radionov.tfcontests.data.repositories

import com.radionov.tfcontests.data.datasource.local.Prefs
import com.radionov.tfcontests.data.datasource.remote.FintechApi

/**
 * @author Andrey Radionov
 */
class AuthRepository(private val prefs: Prefs, private val fintechApi: FintechApi) {

    fun getCookies() = prefs.getCookies()

    fun setCookies(cookies: String) {
        prefs.setCookies(cookies)
    }

    fun performLogin(email: String, pass: String) {
        fintechApi.login(email, pass)
    }
}