package com.radionov.tfcontests.ui.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.radionov.tfcontests.data.entities.Problem
import com.radionov.tfcontests.data.entities.Task

/**
 * @author Andrey Radionov
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {

    fun showTasks(tasks: List<Task>)
    fun showContest(task: Task, problems: List<Problem>)
    fun showError()
}
