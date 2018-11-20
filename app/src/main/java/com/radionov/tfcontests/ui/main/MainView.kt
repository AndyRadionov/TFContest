package com.radionov.tfcontests.ui.main

import com.arellomobile.mvp.MvpView

/**
 * @author Andrey Radionov
 */
interface MainView : MvpView {

    fun onLogout()
    fun onLogoutFail()
}