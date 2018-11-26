package com.radionov.tfcontests.ui.profile

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.radionov.tfcontests.data.entities.User
import com.radionov.tfcontests.data.entities.UserWithStatus
import com.radionov.tfcontests.interactors.UserInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@InjectViewState
class ProfilePresenter @Inject constructor(
    private val userInteractor: UserInteractor
) : MvpPresenter<ProfileView>() {

    private var disposable: Disposable? = null

    fun getProfile() {
        disposable?.dispose()
        disposable = userInteractor.getStoredUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                viewState.showProfile(user)
            }, {e->
                viewState.showError()
            })
    }

    fun fetchUpdate() {
        disposable?.dispose()
        disposable = userInteractor.fetchUpdate()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                viewState.showProfile(user)
            }, {
                viewState.showError()
            })
    }

    fun updateProfile(user: User) {
        disposable?.dispose()
        disposable = userInteractor.updateUser(UserWithStatus(user))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.showSuccess()
            }, {
                viewState.showError()
            })
    }
}
