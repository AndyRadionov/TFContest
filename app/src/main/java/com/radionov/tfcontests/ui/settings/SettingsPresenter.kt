package com.radionov.tfcontests.ui.settings

import com.arellomobile.mvp.InjectViewState
import com.radionov.tfcontests.interactors.AuthInteractor
import com.radionov.tfcontests.ui.common.BasePresenter
import com.radionov.tfcontests.utils.NetworkManager
import com.radionov.tfcontests.utils.RxComposers
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@InjectViewState
class SettingsPresenter @Inject constructor(
    private val authInteractor: AuthInteractor
) : BasePresenter<SettingsView>() {

    fun logout() {
        if (isNotConnected()) return
        disposable = authInteractor.logout()
            .compose(rxComposers.getCompletableComposer())
            .subscribe({
                viewState.onLogout()
            }, {
                viewState.onLogoutFail()
            })
    }
}