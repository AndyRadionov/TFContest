package com.radionov.tfcontests.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.radionov.tfcontests.BuildConfig
import com.radionov.tfcontests.ContestApp
import com.radionov.tfcontests.R
import com.radionov.tfcontests.data.entities.User
import com.radionov.tfcontests.utils.InputValidator
import com.radionov.tfcontests.utils.getName
import com.radionov.tfcontests.utils.setName
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import javax.inject.Inject

private const val CURRENT_USER_KEY = "current_user"
private const val BUTTONS_STATE_KEY = "buttons_state"

class ProfileActivity : MvpAppCompatActivity(), ProfileView {

    @Inject
    @InjectPresenter
    lateinit var profilePresenter: ProfilePresenter

    @ProvidePresenter
    fun providePresenter() = profilePresenter
    private lateinit var currentUser: User
    private var buttonsState: Boolean = false

    private val textWatcher = object :TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            changeButtonsState(true)
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        ContestApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init()

        if (savedInstanceState != null && savedInstanceState.containsKey(CURRENT_USER_KEY)) {
            currentUser = savedInstanceState.getParcelable(CURRENT_USER_KEY) as User
            changeButtonsState(savedInstanceState.getBoolean(BUTTONS_STATE_KEY))
        } else {
            profilePresenter.getProfile()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(BUTTONS_STATE_KEY, buttonsState)
        outState.putParcelable(BUTTONS_STATE_KEY, currentUser)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showProfile(user: User) {
        currentUser = user
        swipe_container.isRefreshing = false
        et_name.setText(user.getName())
        et_birthday.setText(user.birthday)
        et_email.setText(user.email)
        et_region.setText(user.region)
        et_university.setText(user.university)
        et_university_grade.setText(user.universityGraduation.toString())
        Picasso.get()
            .load("${BuildConfig.TF_URL}${user.avatar}")
            .placeholder(R.drawable.placeholder_user)
            .centerCrop()
            .fit()
            .into(iv_avatar)
    }

    override fun showSuccess() {
        swipe_container.isRefreshing = false
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
    }

    override fun showError() {
        swipe_container.isRefreshing = false
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
    }

    private fun init() {
        swipe_container.setOnRefreshListener { profilePresenter.fetchUpdate() }
        btn_save.setOnClickListener {
            val name = et_name.text.toString()
            if (InputValidator.isNameValid(name)) {
                parseUserData()
                profilePresenter.updateProfile(currentUser)
                changeButtonsState()
            } else {
                name_layout.error = getString(R.string.name_error)
            }
        }
        btn_cancel.setOnClickListener {
            profilePresenter.getProfile()
            changeButtonsState()
        }
        et_name.addTextChangedListener(textWatcher)
        et_birthday.addTextChangedListener(textWatcher)
        et_region.addTextChangedListener(textWatcher)
        et_university.addTextChangedListener(textWatcher)
        et_university_grade.addTextChangedListener(textWatcher)
    }

    private fun changeButtonsState(isEnabled: Boolean = false) {
        buttonsState = isEnabled
        btn_save.isEnabled = isEnabled
        btn_cancel.isEnabled = isEnabled
    }

    private fun parseUserData() {
        currentUser.setName(et_name.text.toString())
        currentUser.birthday = et_birthday.text.toString()
        currentUser.region = et_region.text.toString()
        currentUser.university = et_university.text.toString()
        currentUser.universityGraduation = et_university_grade.text.toString().toInt()
    }
}
