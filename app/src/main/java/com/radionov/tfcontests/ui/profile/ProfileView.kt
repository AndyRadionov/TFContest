package com.radionov.tfcontests.ui.profile

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.radionov.tfcontests.data.entities.User

/**
 * @author Andrey Radionov
 */
@StateStrategyType(SkipStrategy::class)
interface ProfileView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProfile(user: User)
    fun showSuccess()
    fun showError()
    fun onNameInput()
    fun onNameInputFail(errorStringId: Int)
}