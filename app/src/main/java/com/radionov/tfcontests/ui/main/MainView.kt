package com.radionov.tfcontests.ui.main

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.radionov.tfcontests.data.entities.Task
import com.radionov.tfcontests.ui.common.BaseView

/**
 * @author Andrey Radionov
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : BaseView {

    fun showTasks(tasks: List<Task>, done: Int, points: Float)
    fun openContest(contestUrl: String)
    @StateStrategyType(SkipStrategy::class)
    fun showError(errorResource: Int)
    @StateStrategyType(SkipStrategy::class)
    fun showSuccess(successResource: Int)
}
