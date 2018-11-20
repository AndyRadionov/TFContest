package com.radionov.tfcontests.ui.login

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * @author Andrey Radionov
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface LoginView: MvpView {

    fun onLogin()
    fun onLoginFail()
    fun onRestorePass()
    fun onRestorePassFail()
}