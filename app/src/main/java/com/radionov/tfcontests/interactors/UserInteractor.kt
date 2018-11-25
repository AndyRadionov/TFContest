package com.radionov.tfcontests.interactors

import com.radionov.tfcontests.data.entities.UserWithStatus
import com.radionov.tfcontests.data.repositories.UserRepository
import io.reactivex.Completable
import io.reactivex.Single

/**
 * @author Andrey Radionov
 */
class UserInteractor(private val userRepository: UserRepository) {

    fun fetchUpdate(): Single<UserWithStatus> {
        return userRepository.getRemoteUser()
            .doOnSuccess { user -> userRepository.saveLocalUser(user.user) }
    }

    fun updateUser(user: UserWithStatus): Completable {
        return userRepository.updateRemoteUser(user)
            .doOnComplete {
                userRepository.saveLocalUser(user.user)
            }
    }

    fun getStoredUser() = userRepository.getLocalUser()
}