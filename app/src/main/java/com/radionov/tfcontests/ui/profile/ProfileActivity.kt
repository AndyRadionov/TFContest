package com.radionov.tfcontests.ui.profile

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.radionov.tfcontests.BuildConfig
import com.radionov.tfcontests.ContestApp
import com.radionov.tfcontests.R
import com.radionov.tfcontests.data.entities.User
import com.radionov.tfcontests.utils.getName
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import javax.inject.Inject

class ProfileActivity : MvpAppCompatActivity(), ProfileView {

    @Inject
    @InjectPresenter
    lateinit var profilePresenter: ProfilePresenter

    @ProvidePresenter
    fun providePresenter() = profilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        ContestApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        profilePresenter.getProfile()
        swipe_container.setOnRefreshListener { profilePresenter.fetchUpdate() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showProfile(user: User) {
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
}
