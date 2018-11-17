package com.radionov.tfcontests.ui.login

import android.content.Intent
import android.os.Bundle
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

        btn_login.setOnClickListener { loginPresenter
            .login(name_input.text.toString(), pass_input.text.toString()) }
    }

    override fun onLoginFailed() {
        Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
    }

    override fun onLoginSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
