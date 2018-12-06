package com.radionov.tfcontests.ui.main

import com.arellomobile.mvp.InjectViewState
import com.radionov.tfcontests.R
import com.radionov.tfcontests.data.entities.Task
import com.radionov.tfcontests.interactors.ContestInteractor
import com.radionov.tfcontests.ui.common.BasePresenter
import com.radionov.tfcontests.utils.TaskStatuses
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@InjectViewState
class MainPresenter @Inject constructor(
    private val contestInteractor: ContestInteractor
) : BasePresenter<MainView>() {

    fun getHomeWorks() {
        if (isNotConnected()) return
        disposable = contestInteractor.getHomeWorks()
            .map { tasks ->
                if (tasks.isNotEmpty()) {
                    tasks.sortWith(Comparator<Task> { o1, o2 ->
                        if (o1.status == o2.status) return@Comparator 0

                        val status1 = TaskStatuses.valueOf(o1.task.contestInfo.contestStatus.status.toUpperCase())
                        val status2 = TaskStatuses.valueOf(o2.task.contestInfo.contestStatus.status.toUpperCase())
                        return@Comparator status1.compareTo(status2)
                    })
                }
                val doneAndPoints = tasks.fold(0 to 0F) { doneAndPoints, task ->
                    val done = doneAndPoints.first +
                            if (TaskStatuses.valueOf(task.status.toUpperCase()) == TaskStatuses.ACCEPTED) 1 else 0
                    val points = doneAndPoints.second + task.mark.toFloat()
                    return@fold done to points
                }

                return@map tasks to doneAndPoints
            }
            .compose(rxComposers.getSingleComposer())
            .subscribe({ result ->
                if (result.first.isNotEmpty()) {
                    viewState.showTasks(result.first, result.second.first, result.second.second)
                } else {
                    viewState.showError(R.string.error_tests_not_found)
                }
            }, { viewState.showError(R.string.error_tests_not_found) })
    }

    fun getContest(task: Task) {
        if (!checkTask(task)) return

        viewState.openContest(task.task.contestInfo.contestUrl)
    }

    private fun checkTask(task: Task): Boolean {
        when (task.task.contestInfo.contestStatus.status) {
            TaskStatuses.ONGOING.title -> return true
            TaskStatuses.ANNOUNCEMENT.title -> {
                viewState.showError(R.string.error_test_announce)
                return false
            }
        }
        when (task.status) {
            TaskStatuses.FAILED.title -> viewState.showError(R.string.error_test_failed)
            TaskStatuses.ACCEPTED.title -> viewState.showSuccess(R.string.success_test_pass)
        }
        return false
    }
}
