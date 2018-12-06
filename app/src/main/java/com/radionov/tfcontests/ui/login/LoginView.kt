package com.radionov.tfcontests.ui.login

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.radionov.tfcontests.ui.common.BaseView

/**
 * @author Andrey Radionov
 */
@StateStrategyType(SkipStrategy::class)
interface LoginView: BaseView {

    fun onLogin()
    fun onRestorePass()
    fun onError(messageId: Int)
}