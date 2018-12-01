package com.radionov.tfcontests.ui.splash

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.radionov.tfcontests.interactors.AuthInteractor
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@InjectViewState
class SplashPresenter @Inject constructor(
    private val authInteractor: AuthInteractor
) : MvpPresenter<SplashView>() {

    fun checkLogin() {
        if (authInteractor.checkLogin()) {
            viewState.openMainScreen()
        } else {
            viewState.openLoginScreen()
        }
    }
}
