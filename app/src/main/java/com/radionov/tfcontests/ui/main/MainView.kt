package com.radionov.tfcontests.ui.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.radionov.tfcontests.data.entities.Problem
import com.radionov.tfcontests.data.entities.Task

/**
 * @author Andrey Radionov
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {

    fun showTasks(tasks: List<Task>, done: Int, points: Float)
    fun openContest(contestUrl: String)
    @StateStrategyType(SkipStrategy::class)
    fun showError(errorResource: Int)
    @StateStrategyType(SkipStrategy::class)
    fun showSuccess(successResource: Int)
}
