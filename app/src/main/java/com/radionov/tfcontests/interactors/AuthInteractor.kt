package com.radionov.tfcontests.interactors

import com.radionov.tfcontests.data.repositories.AuthRepository
import com.radionov.tfcontests.data.repositories.UserRepository

/**
 * @author Andrey Radionov
 */
class AuthInteractor(private val authRepository: AuthRepository,
                     private val userRepository: UserRepository) {

    fun checkLogin() =
        authRepository.getAuthCookie() != null

    fun login(email: String, pass: String) =
        authRepository.login(email, pass)
            .doOnSuccess { user ->
                if (user.email != null) {
                    userRepository.saveLocalUser(user)
                }
            }

    fun logout() = authRepository.logout()
        .doOnComplete {
            authRepository.removeCookies()
            userRepository.removeLocalUser()
        }

    fun restorePass(email: String) = authRepository.restorePass(email)
}
