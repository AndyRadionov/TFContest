package com.radionov.tfcontests.interactors

import com.radionov.tfcontests.data.repositories.AuthRepository

/**
 * @author Andrey Radionov
 */
class AuthInteractor(private val authRepository: AuthRepository) {

    fun checkLogin(): Boolean = false


}