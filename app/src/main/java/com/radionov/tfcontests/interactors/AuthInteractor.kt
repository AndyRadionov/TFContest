package com.radionov.tfcontests.interactors

import com.radionov.tfcontests.data.repositories.AuthRepository

/**
 * @author Andrey Radionov
 */
class AuthInteractor(private val authRepository: AuthRepository) {

    fun checkLogin() =
        authRepository.getAuthCookie() != null

    fun login(email: String, pass: String) =
        authRepository.login(email, pass)
            .doOnSuccess { user ->
                if (user.email != null) {
                    authRepository.saveUser(user)
                }
            }

    fun logout() = authRepository.logout()
        .doOnComplete {
            authRepository.clearAuthData()
        }

    fun restorePass(email: String) = authRepository.restorePass(email)
}
