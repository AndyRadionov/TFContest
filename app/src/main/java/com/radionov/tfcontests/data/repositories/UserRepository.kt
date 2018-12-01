package com.radionov.tfcontests.data.repositories

import com.radionov.tfcontests.data.datasource.local.Prefs
import com.radionov.tfcontests.data.datasource.local.db.UserDao
import com.radionov.tfcontests.data.datasource.remote.FintechApi
import com.radionov.tfcontests.data.entities.User
import com.radionov.tfcontests.data.entities.UserWithStatus
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers


/**
 * @author Andrey Radionov
 */
class UserRepository(
    private val userDao: UserDao,
    private val prefs: Prefs,
    private val fintechApi: FintechApi
) {

    fun getRemoteUser() = fintechApi.getUser()

    fun updateRemoteUser(user: UserWithStatus) =
        fintechApi.updateUser(user.user)

    fun getLocalUser() = userDao.getUser()

    fun saveLocalUser(user: User) {
        Completable.fromAction {
            userDao.saveUser(user)
        }.subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun removeLocalUser() {
        Completable.fromAction {
            userDao.removeUser()
        }.subscribeOn(Schedulers.io())
            .subscribe()
    }
}
