package com.radionov.tfcontests.ui.login

import com.arellomobile.mvp.InjectViewState
import com.radionov.tfcontests.R
import com.radionov.tfcontests.interactors.AuthInteractor
import com.radionov.tfcontests.ui.common.BasePresenter
import com.radionov.tfcontests.utils.NetworkManager
import com.radionov.tfcontests.utils.RxComposers
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@InjectViewState
class LoginPresenter @Inject constructor(
    private val authInteractor: AuthInteractor
) : BasePresenter<LoginView>() {

    fun login(email: String, pass: String) {
        if (authInteractor.checkLogin()) {
            viewState.onLogin()
            return
        }

        if (isNotConnected()) return
        disposable = authInteractor.login(email, pass)
            .compose(rxComposers.getSingleComposer())
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
        if (isNotConnected()) return
        disposable = authInteractor.restorePass(email)
            .compose(rxComposers.getCompletableComposer())
            .subscribe({
                viewState.onRestorePass()
            }, {
                viewState.onError(R.string.error_restore)
            })
    }
}
