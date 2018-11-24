package com.radionov.tfcontests.ui.settings

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.radionov.tfcontests.ContestApp
import com.radionov.tfcontests.R
import com.radionov.tfcontests.ui.login.LoginActivity
import com.radionov.tfcontests.ui.main.MainPresenter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_settings.*
import javax.inject.Inject

class SettingsActivity : MvpAppCompatActivity(), SettingsView {

    @Inject
    @InjectPresenter
    lateinit var settingsPresenter: SettingsPresenter

    @ProvidePresenter
    fun providePresenter() = settingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        ContestApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        btn_logout.setOnClickListener { settingsPresenter.logout() }
    }

    override fun onLogoutFail() {
        Toasty.error(this, "Logout Fail", Toast.LENGTH_SHORT).show()
    }

    override fun onLogout() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
