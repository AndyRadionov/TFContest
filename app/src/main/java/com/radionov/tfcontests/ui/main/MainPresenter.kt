package com.radionov.tfcontests.ui.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.radionov.tfcontests.interactors.AuthInteractor
import com.radionov.tfcontests.interactors.ContestInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@InjectViewState
class MainPresenter @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val contestInteractor: ContestInteractor)
    : MvpPresenter<MainView>() {

    private var disposable: Disposable? = null

    fun getHomeWorks() {
        disposable?.dispose()

        disposable = contestInteractor.getHomeWorks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ tasks ->
                if (tasks != null && tasks.isNotEmpty()) {
                    viewState.showTasks(tasks)
                } else {
                    viewState.showError()
                }
            }, {
                viewState.showError()
            })
    }
}