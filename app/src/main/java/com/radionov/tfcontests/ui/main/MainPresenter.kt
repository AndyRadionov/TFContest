package com.radionov.tfcontests.ui.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.radionov.tfcontests.interactors.AuthInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@InjectViewState
class MainPresenter @Inject constructor(private val authInteractor: AuthInteractor)
    : MvpPresenter<MainView>() {

    fun logout() {
        authInteractor.logout()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.onLogout()
            }, {
                    e ->
                println()
            })
    }
}