package com.radionov.tfcontests.ui.contest

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.radionov.tfcontests.data.entities.Problem
import com.radionov.tfcontests.ui.common.BaseView

/**
 * @author Andrey Radionov
 */
interface ContestView: BaseView {

    @StateStrategyType(SkipStrategy::class)
    fun showError(errorResource: Int)

    fun showContest(title: String, problems: List<Problem>)
}