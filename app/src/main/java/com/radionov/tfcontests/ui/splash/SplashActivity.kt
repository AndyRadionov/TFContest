package com.radionov.tfcontests.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.radionov.tfcontests.ContestApp
import com.radionov.tfcontests.R
import com.radionov.tfcontests.ui.common.BaseActivity
import com.radionov.tfcontests.ui.login.LoginActivity
import com.radionov.tfcontests.ui.main.MainActivity
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashView {

    @Inject
    @InjectPresenter
    lateinit var presenter: SplashPresenter
    private var timeoutHandler: Handler? = null

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        ContestApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter.checkLogin()
    }

    override fun openLoginScreen() {
        openScreen(LoginActivity::class.java)
    }

    override fun openMainScreen() {
        openScreen(MainActivity::class.java)
    }

    private fun openScreen(screen: Class<out Activity>) {
        if (timeoutHandler == null) {
            timeoutHandler = Handler()
            timeoutHandler?.postDelayed({
                val intent = Intent(this, screen)
                startActivity(intent)
            }, SPLASH_TIME_OUT)
        }
    }

    companion object {
        const val SPLASH_TIME_OUT = 2000L
    }
}
