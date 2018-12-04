package com.radionov.tfcontests.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.radionov.tfcontests.ContestApp
import com.radionov.tfcontests.R
import com.radionov.tfcontests.ui.main.MainActivity
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : MvpAppCompatActivity(), LoginView {

    @Inject
    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter

    @ProvidePresenter
    fun providePresenter() = loginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        ContestApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()
    }

    override fun onLogin() {
        setProgressView(View.INVISIBLE, View.VISIBLE, true)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onRestorePass() {
        setProgressView(View.INVISIBLE, View.VISIBLE, true)
        Toasty.success(this, getString(R.string.restore_sent, email_input.text), Toast.LENGTH_SHORT).show()
    }

    override fun onError(messageId: Int) {
        setProgressView(View.INVISIBLE, View.VISIBLE, true)
        Toasty.error(this, getString(messageId), Toast.LENGTH_SHORT).show()
    }

    private fun initViews() {
        btn_login.setOnClickListener {
            val email = email_input.text.toString()
            setProgressView(View.VISIBLE, View.INVISIBLE, false)
            if (isLoginState()) {
                loginPresenter
                    .login(email, pass_input.text.toString())
            } else {
                loginPresenter.restorePass(email)
            }
        }

        btn_restore_pass.setOnClickListener {
            setRestorePassState()
        }
    }

    private fun setRestorePassState() {
        btn_restore_pass.text = btn_login.text
        val (passVisibility, btnText) = if (isLoginState()) {
            View.GONE to getString(R.string.restore_pass)
        } else {
            View.VISIBLE to getString(R.string.login)
        }
        pass_input_layout.visibility = passVisibility
        btn_login.text = btnText
    }

    private fun isLoginState() = pass_input_layout.visibility == View.VISIBLE

    private fun setProgressView(progressVisibility: Int, buttonVisibility: Int, buttonsEnabled: Boolean) {
        pb_loading.visibility = progressVisibility
        btn_login.visibility = buttonVisibility
        email_input.isEnabled = buttonsEnabled
        pass_input.isEnabled = buttonsEnabled
        btn_restore_pass.isEnabled = buttonsEnabled
    }
}
