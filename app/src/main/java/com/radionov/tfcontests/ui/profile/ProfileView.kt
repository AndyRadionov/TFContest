package com.radionov.tfcontests.ui.profile

import com.arellomobile.mvp.MvpView
import com.radionov.tfcontests.data.entities.User

/**
 * @author Andrey Radionov
 */
interface ProfileView: MvpView {

    fun showProfile(user: User)
    fun showError()
}