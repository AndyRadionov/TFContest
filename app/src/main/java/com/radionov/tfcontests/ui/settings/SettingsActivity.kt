package com.radionov.tfcontests.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.radionov.tfcontests.ContestApp
import com.radionov.tfcontests.R
import com.radionov.tfcontests.ui.common.BaseActivity
import com.radionov.tfcontests.ui.login.LoginActivity
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_settings.*
import javax.inject.Inject

class SettingsActivity : BaseActivity(), SettingsView {

    @Inject
    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        ContestApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_logout.setOnClickListener { presenter.logout() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onLogoutFail() {
        Toasty.error(this, getString(R.string.logout_error), Toast.LENGTH_SHORT).show()
    }

    override fun onLogout() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
