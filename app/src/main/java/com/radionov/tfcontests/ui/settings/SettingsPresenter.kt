package com.radionov.tfcontests.ui.settings

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.radionov.tfcontests.interactors.AuthInteractor
import com.radionov.tfcontests.utils.RxComposers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@InjectViewState
class SettingsPresenter @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val rxComposers: RxComposers
) : MvpPresenter<SettingsView>() {

    private var disposable: Disposable? = null

    fun logout() {

        disposable?.dispose()

        disposable = authInteractor.logout()
            .compose(rxComposers.getCompletableComposer())
            .subscribe({
                viewState.onLogout()
            }, {
                viewState.onLogoutFail()
            })
    }
}