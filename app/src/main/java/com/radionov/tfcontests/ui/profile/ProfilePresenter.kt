package com.radionov.tfcontests.ui.profile

import com.arellomobile.mvp.InjectViewState
import com.radionov.tfcontests.R
import com.radionov.tfcontests.data.entities.User
import com.radionov.tfcontests.data.entities.UserWithStatus
import com.radionov.tfcontests.interactors.UserInteractor
import com.radionov.tfcontests.ui.common.BasePresenter
import com.radionov.tfcontests.utils.InputValidator
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@InjectViewState
class ProfilePresenter @Inject constructor(
    private val userInteractor: UserInteractor
) : BasePresenter<ProfileView>() {

    fun getProfile() {
        if (isNotConnected()) return
        disposable = userInteractor.getStoredUser()
            .compose(rxComposers.getSingleComposer())
            .subscribe({ user ->
                viewState.showProfile(user)
            }, {
                viewState.showError()
            })
    }

    fun fetchUpdate() {
        if (isNotConnected()) return
        disposable = userInteractor.fetchUpdate()
            .compose(rxComposers.getSingleComposer())
            .subscribe({ user ->
                viewState.showProfile(user)
            }, {
                viewState.showError()
            })
    }

    fun updateProfile(user: User) {
        if (isNotConnected()) return
        disposable = userInteractor.updateUser(UserWithStatus(user))
            .compose(rxComposers.getCompletableComposer())
            .subscribe({
                viewState.showSuccess()
            }, {
                viewState.showError()
            })
    }

    fun isNameValid(name: String) {
        when {
            InputValidator.isNameNotRus(name) -> viewState.onNameInputFail(R.string.name_rus_error)
            InputValidator.isNameNotFull(name) -> viewState.onNameInputFail(R.string.name_full_error)
            else -> viewState.onNameInput()
        }
    }
}
