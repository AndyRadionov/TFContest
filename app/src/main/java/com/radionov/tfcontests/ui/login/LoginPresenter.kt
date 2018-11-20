package com.radionov.tfcontests.ui.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.radionov.tfcontests.R
import com.radionov.tfcontests.interactors.AuthInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@InjectViewState
class LoginPresenter @Inject constructor(private val authInteractor: AuthInteractor) : MvpPresenter<LoginView>() {

    var disposable: Disposable? = null

    fun login(email: String, pass: String) {
        if (authInteractor.checkLogin()) {
            viewState.onLogin()
            return
        }

        disposable?.dispose()

        disposable = authInteractor.login(email, pass)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ login ->
                if (login.email == null) {
                    viewState.onError(R.string.error_login)
                } else {
                    viewState.onLogin()
                }
            }, {
                viewState.onError(R.string.error_login)
            })
    }

    fun restorePass(email: String) {
        disposable?.dispose()

        disposable = authInteractor.restorePass(email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.onRestorePass()
            }, {
                viewState.onError(R.string.error_restore)
            })
    }
}
