package com.radionov.tfcontests.ui.splash

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.radionov.tfcontests.R
import com.radionov.tfcontests.TFContestsApp
import com.radionov.tfcontests.ui.login.LoginActivity
import com.radionov.tfcontests.ui.login.LoginPresenter
import com.radionov.tfcontests.ui.login.LoginView
import com.radionov.tfcontests.ui.main.MainActivity
import javax.inject.Inject

class SplashActivity : MvpAppCompatActivity(), SplashView {

    @Inject
    @InjectPresenter
    lateinit var splashPresenter: SplashPresenter

    @ProvidePresenter
    fun providePresenter() = splashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        TFContestsApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashPresenter.checkLogin()
    }

    override fun openLoginScreen() {
        openScreen(LoginActivity::class.java)
    }

    override fun openMainScreen() {
        openScreen(MainActivity::class.java)
    }

    private fun openScreen(screen: Class<out Activity>) {
        val intent = Intent(this, screen)
        startActivity(intent)
    }
}
