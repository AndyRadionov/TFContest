package com.radionov.tfcontests.ui.profile

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.radionov.tfcontests.R
import com.radionov.tfcontests.data.entities.User
import com.radionov.tfcontests.data.entities.UserWithStatus
import com.radionov.tfcontests.interactors.UserInteractor
import com.radionov.tfcontests.utils.InputValidator
import com.radionov.tfcontests.utils.RxComposers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@InjectViewState
class ProfilePresenter @Inject constructor(
    private val userInteractor: UserInteractor,
    private val rxComposers: RxComposers
) : MvpPresenter<ProfileView>() {

    private var disposable: Disposable? = null

    fun getProfile() {
        disposable?.dispose()
        disposable = userInteractor.getStoredUser()
            .compose(rxComposers.getSingleComposer())
            .subscribe({ user ->
                viewState.showProfile(user)
            }, {e->
                viewState.showError()
            })
    }

    fun fetchUpdate() {
        disposable?.dispose()
        disposable = userInteractor.fetchUpdate()
            .compose(rxComposers.getSingleComposer())
            .subscribe({ user ->
                viewState.showProfile(user)
            }, {
                viewState.showError()
            })
    }

    fun updateProfile(user: User) {
        disposable?.dispose()
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
            InputValidator.isNameNotRus(name) -> viewState.onNameInputFail(R.string.name_full_error)
            InputValidator.isNameNotFull(name) -> viewState.onNameInputFail(R.string.name_full_error)
            else -> viewState.onNameInput()
        }
    }
}
