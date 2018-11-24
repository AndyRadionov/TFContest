package com.radionov.tfcontests.ui.settings

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * @author Andrey Radionov
 */
@StateStrategyType(SkipStrategy::class)
interface SettingsView: MvpView {

    fun onLogout()

    fun onLogoutFail()
}
