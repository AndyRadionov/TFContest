package com.radionov.tfcontests.ui.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.radionov.tfcontests.data.entities.Task

/**
 * @author Andrey Radionov
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun onLogout()

    @StateStrategyType(SkipStrategy::class)
    fun onLogoutFail()

    fun showTasks(tasks: List<Task>)

    fun showError()
}