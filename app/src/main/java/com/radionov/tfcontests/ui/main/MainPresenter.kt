package com.radionov.tfcontests.ui.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.radionov.tfcontests.data.entities.Task
import com.radionov.tfcontests.interactors.ContestInteractor
import com.radionov.tfcontests.utils.RxComposers
import com.radionov.tfcontests.utils.TaskStatuses
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@InjectViewState
class MainPresenter @Inject constructor(
    private val contestInteractor: ContestInteractor,
    private val rxComposers: RxComposers
) : MvpPresenter<MainView>() {

    private var disposable: Disposable? = null

    fun getHomeWorks() {
        disposable?.dispose()

        disposable = contestInteractor.getHomeWorks()
            .compose(rxComposers.getSingleComposer())
            .subscribe({ tasks ->
                if (tasks != null && tasks.isNotEmpty()) {
                    tasks.sortWith(Comparator<Task> { o1, o2 ->
                        if (o1.status == o2.status) return@Comparator 0

                        val status1 = TaskStatuses.valueOf(o1.task.contestInfo.contestStatus.status.toUpperCase())
                        val status2 = TaskStatuses.valueOf(o2.task.contestInfo.contestStatus.status.toUpperCase())
                        return@Comparator status1.compareTo(status2)
                    })
                    viewState.showTasks(tasks)
                } else {
                    viewState.showError()
                }
            }, { viewState.showError() })
    }

    fun getContest(task: Task) {
        disposable?.dispose()

        disposable = contestInteractor.getContest(task.task.contestInfo.contestUrl)
            .compose(rxComposers.getSingleComposer())
            .subscribe({ problems ->
                if (problems != null && problems.isNotEmpty()) {
                    viewState.showContest(task, problems)
                } else {
                    viewState.showError()
                }
            }, { viewState.showError() })
    }
}