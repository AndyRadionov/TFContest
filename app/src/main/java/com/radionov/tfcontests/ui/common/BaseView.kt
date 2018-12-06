package com.radionov.tfcontests.ui.common

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * @author Andrey Radionov
 */
interface BaseView: MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun showNotConnected()
}