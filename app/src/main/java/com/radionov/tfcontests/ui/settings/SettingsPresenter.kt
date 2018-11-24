package com.radionov.tfcontests.ui.settings

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.radionov.tfcontests.interactors.AuthInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@InjectViewState
class SettingsPresenter @Inject constructor(
    private val authInteractor: AuthInteractor) : MvpPresenter<SettingsView>() {

    private var disposable: Disposable? = null

    fun logout() {

        disposable?.dispose()

        disposable = authInteractor.logout()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.onLogout()
            }, {
                viewState.onLogoutFail()
            })
    }
}