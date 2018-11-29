package com.radionov.tfcontests.ui.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.radionov.tfcontests.data.entities.Task
import com.radionov.tfcontests.interactors.AuthInteractor
import com.radionov.tfcontests.interactors.ContestInteractor
import com.radionov.tfcontests.utils.TaskStatuses
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@InjectViewState
class MainPresenter @Inject constructor(
    private val contestInteractor: ContestInteractor
) : MvpPresenter<MainView>() {

    private var disposable: Disposable? = null

    fun getHomeWorks() {
        disposable?.dispose()

        disposable = contestInteractor.getHomeWorks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ tasks ->
                if (tasks != null && tasks.isNotEmpty()) {
                    tasks.sortWith(Comparator<Task> { o1, o2 ->
                        if (o1.status == o2.status) return@Comparator 0

                        val status1 = TaskStatuses.valueOf(o1.status.toUpperCase())
                        val status2 = TaskStatuses.valueOf(o2.status.toUpperCase())
                        return@Comparator status1.compareTo(status2)
                    })
                    viewState.showTasks(tasks)
                } else {
                    viewState.showError()
                }
            }, {
                viewState.showError()
            })
    }
}