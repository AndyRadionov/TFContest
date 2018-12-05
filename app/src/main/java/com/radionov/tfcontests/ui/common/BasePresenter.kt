package com.radionov.tfcontests.ui.common

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.radionov.tfcontests.utils.RxComposers
import io.reactivex.disposables.Disposable

/**
 * @author Andrey Radionov
 */
abstract class BasePresenter<T : MvpView>(protected val rxComposers: RxComposers) : MvpPresenter<T>() {
    protected var disposable: Disposable? = null

    protected fun dispose() {
        disposable?.dispose()
    }
}