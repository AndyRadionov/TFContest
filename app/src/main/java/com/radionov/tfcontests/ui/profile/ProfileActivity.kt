package com.radionov.tfcontests.ui.profile

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.radionov.tfcontests.BuildConfig
import com.radionov.tfcontests.ContestApp
import com.radionov.tfcontests.R
import com.radionov.tfcontests.data.entities.User
import com.radionov.tfcontests.ui.common.BaseActivity
import com.radionov.tfcontests.utils.EMPTY_STRING
import com.radionov.tfcontests.utils.formatBirthday
import com.radionov.tfcontests.utils.getName
import com.radionov.tfcontests.utils.setName
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.dialog_year_picker.*
import java.util.*
import javax.inject.Inject

class ProfileActivity : BaseActivity(), ProfileView {

    @Inject
    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private lateinit var currentUser: User
    private var buttonsState: Boolean = false

    private val textWatcher = initTextWatcher()

    override fun onCreate(savedInstanceState: Bundle?) {
        ContestApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
        initDatePickers()

        if (savedInstanceState != null && savedInstanceState.containsKey(CURRENT_USER_KEY)) {
            currentUser = savedInstanceState.getParcelable(CURRENT_USER_KEY) as User
            changeButtonsState(savedInstanceState.getBoolean(BUTTONS_STATE_KEY))
            showProfile(currentUser)
        } else {
            presenter.getProfile()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(BUTTONS_STATE_KEY, buttonsState)
        outState?.putParcelable(BUTTONS_STATE_KEY, currentUser)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
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
        et_work.setText(user.currentWork)
        et_phone.setText(user.phone)
        changeButtonsState()
        Picasso.get()
            .load("${BuildConfig.TF_URL}${user.avatar}")
            .placeholder(R.drawable.placeholder_user)
            .centerCrop()
            .fit()
            .into(iv_avatar)
    }

    override fun showSuccess() {
        swipe_container.isRefreshing = false
        changeButtonsState()
        Toasty.success(this, getString(R.string.profile_updated), Toast.LENGTH_SHORT).show()
    }

    override fun showError() {
        swipe_container.isRefreshing = false
        Toasty.error(this, getString(R.string.error_update_profile), Toast.LENGTH_SHORT).show()
    }

    override fun onInputSuccess() {
        name_layout.error = EMPTY_STRING
        phone_layout.error = EMPTY_STRING
        parseUserData()
        presenter.updateProfile(currentUser)
        changeButtonsState()
    }

    override fun onNameInputFail(errorStringId: Int) {
        name_layout.error = getString(errorStringId)
    }

    override fun onPhoneInputFail() {
        phone_layout.error = getString(R.string.error_phone)
    }

    private fun init() {
        swipe_container.isRefreshing = true
        swipe_container.setOnRefreshListener { presenter.fetchUpdate() }
        btn_save.setOnClickListener {
            val name = et_name.text.toString()
            val phone = et_phone.text.toString()
            presenter.isInputValid(name, phone)
        }
        btn_cancel.setOnClickListener {
            presenter.getProfile()
            changeButtonsState()
        }
        et_name.addTextChangedListener(textWatcher)
        et_birthday.addTextChangedListener(textWatcher)
        et_region.addTextChangedListener(textWatcher)
        et_university.addTextChangedListener(textWatcher)
        et_university_grade.addTextChangedListener(textWatcher)
        et_work.addTextChangedListener(textWatcher)
        et_phone.addTextChangedListener(textWatcher)
    }

    private fun changeButtonsState(isEnabled: Boolean = false) {
        buttonsState = isEnabled
        btn_save.isEnabled = isEnabled
        btn_cancel.isEnabled = isEnabled
    }

    private fun parseUserData() {
        with(currentUser) {
            setName(et_name.text.toString())
            birthday = et_birthday.text.toString()
            region = et_region.text.toString()
            university = et_university.text.toString()
            universityGraduation = et_university_grade.text.toString().toInt()
            currentWork = et_work.text.toString()
            phone = et_phone.text.toString()
        }
    }

    private fun initDatePickers() {
        val calendar = Calendar.getInstance()

        val birthDate = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            et_birthday.setText(formatBirthday(calendar.time))
        }
        et_birthday.setOnClickListener {
            val dialog = DatePickerDialog(
                this@ProfileActivity, birthDate, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.datePicker.minDate = MIN_BIRTH_YEAR
            dialog.show()
        }

        et_university_grade.setOnClickListener {
            showYearDialog()
        }
    }

    private fun showYearDialog() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val dialog = Dialog(this@ProfileActivity)
        dialog.setContentView(R.layout.dialog_year_picker)
        with(dialog) {
            year_picker.maxValue = currentYear + MAX_GRADE_STEP
            year_picker.value = currentYear
            year_picker.minValue = MIN_GRADE_YEAR
            tv_year_text.text = currentYear.toString()
            year_picker.wrapSelectorWheel = false
            year_picker.setOnValueChangedListener { _, _, newVal -> tv_year_text.text = newVal.toString() }
            btn_ok_year.setOnClickListener {
                this@ProfileActivity.et_university_grade.setText(year_picker.value.toString())
                dismiss()
            }

            btn_cancel_year.setOnClickListener { dismiss() }
            show()
        }
    }

    private fun initTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                changeButtonsState(true)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {/*NOP*/}
            override fun afterTextChanged(s: Editable?) {/*NOP*/}
        }
    }

    companion object {
        private const val CURRENT_USER_KEY = "current_user"
        private const val BUTTONS_STATE_KEY = "buttons_state"
        private const val MIN_BIRTH_YEAR = 1950L
        private const val MIN_GRADE_YEAR = 1970
        private const val MAX_GRADE_STEP = 10
    }
}
