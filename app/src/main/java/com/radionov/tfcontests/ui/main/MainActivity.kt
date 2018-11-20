package com.radionov.tfcontests.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.radionov.tfcontests.R
import com.radionov.tfcontests.ContestApp
import com.radionov.tfcontests.ui.login.LoginActivity
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter
    @ProvidePresenter
    fun providePresenter() = mainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        ContestApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_logout.setOnClickListener { mainPresenter.logout() }
    }

    override fun onLogout() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onLogoutFail() {
        Toasty.error(this, "Logout Fail", Toast.LENGTH_SHORT).show()
    }
}
