package com.radionov.tfcontests.ui.settings

import com.arellomobile.mvp.InjectViewState
import com.radionov.tfcontests.interactors.AuthInteractor
import com.radionov.tfcontests.ui.common.BasePresenter
import com.radionov.tfcontests.utils.RxComposers
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@InjectViewState
class SettingsPresenter @Inject constructor(
    private val authInteractor: AuthInteractor,
    rxComposers: RxComposers
) : BasePresenter<SettingsView>(rxComposers) {

    fun logout() {
        dispose()
        disposable = authInteractor.logout()
            .compose(rxComposers.getCompletableComposer())
            .subscribe({
                viewState.onLogout()
            }, {
                viewState.onLogoutFail()
            })
    }
}