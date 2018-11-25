package com.radionov.tfcontests.data.repositories

import com.radionov.tfcontests.data.datasource.local.db.UserDao
import com.radionov.tfcontests.data.datasource.remote.FintechApi
import com.radionov.tfcontests.data.entities.User
import com.radionov.tfcontests.data.entities.UserWithStatus
import io.reactivex.Completable

/**
 * @author Andrey Radionov
 */
class UserRepository(
    private val userDao: UserDao,
    private val fintechApi: FintechApi
) {

    fun getRemoteUser() = fintechApi.getUser()

    fun updateRemoteUser(user: UserWithStatus) = fintechApi.updateUser(user)

    fun getLocalUser() = userDao.getUser()

    fun saveLocalUser(user: User) {
        Completable.fromAction {
            userDao.saveUser(user)
        }
    }

    fun removeLocalUser() {
        Completable.fromAction {
            userDao.removeUser()
        }
    }
}