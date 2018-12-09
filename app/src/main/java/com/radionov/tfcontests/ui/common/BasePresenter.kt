package com.radionov.tfcontests.ui.common

import com.arellomobile.mvp.MvpPresenter
import com.radionov.tfcontests.utils.NetworkManager
import com.radionov.tfcontests.utils.RxComposers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
abstract class BasePresenter<T : BaseView> : MvpPresenter<T>() {

    @Inject lateinit var rxComposers: RxComposers
    @Inject lateinit var networkManager: NetworkManager
    protected var disposable: Disposable? = null

    private fun dispose() {
        disposable?.dispose()
    }

    protected fun isNotConnected(): Boolean {
        dispose()
        if (!networkManager.isInternetAvailable()) {
            viewState.showNotConnected()
            return true
        }
        return false
    }
}