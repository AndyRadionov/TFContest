package com.radionov.tfcontests.ui.login

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
class LoginPresenter @Inject constructor(private val authInteractor: AuthInteractor)
    : MvpPresenter<LoginView>() {

    var disposable: Disposable? = null
    fun login(email: String, pass: String) {
        if (authInteractor.checkLogin()) {
            viewState.onLoginSuccess()
        }

        disposable?.dispose()

        disposable = authInteractor.login(email, pass)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ login ->
                if (login?.details != null) {
                    viewState.onLoginFailed()
                } else {
                    viewState.onLoginSuccess()

                }
            }, { e->
            viewState.onLoginFailed()
        })
    }
}