package com.radionov.tfcontests.ui.contest

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.radionov.tfcontests.data.entities.ContestResponse
import com.radionov.tfcontests.data.entities.Problem

/**
 * @author Andrey Radionov
 */
interface ContestView: MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun showError(errorResource: Int)

    fun showContest(title: String, problems: List<Problem>)
}