package com.radionov.tfcontests.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.radionov.tfcontests.R
import com.radionov.tfcontests.TFContestsApp
import com.radionov.tfcontests.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : MvpAppCompatActivity(), LoginView {

    @Inject
    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter

    @ProvidePresenter
    fun providePresenter() = loginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        TFContestsApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()
    }

    override fun onLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onLoginFail() {
        Toast.makeText(this, "Login Fail", Toast.LENGTH_SHORT).show()
    }

    override fun onRestorePass() {
        Toast.makeText(this, "Restore Pass Success", Toast.LENGTH_SHORT).show()
    }

    override fun onRestorePassFail() {
        Toast.makeText(this, "Restore Pass Fail", Toast.LENGTH_SHORT).show()
    }

    private fun initViews() {
        btn_login.setOnClickListener {
            val email = name_input.text.toString()
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
}
