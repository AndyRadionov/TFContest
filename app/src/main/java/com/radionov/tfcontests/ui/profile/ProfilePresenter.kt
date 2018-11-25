package com.radionov.tfcontests.ui.profile

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.radionov.tfcontests.interactors.AuthInteractor
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@InjectViewState
class ProfilePresenter @Inject constructor(
    private val authInteractor: AuthInteractor
) : MvpPresenter<ProfileView>() {

    fun getProfile() {

    }
}