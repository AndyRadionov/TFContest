package com.radionov.tfcontests.ui.contest

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.radionov.tfcontests.ContestApp
import com.radionov.tfcontests.R
import com.radionov.tfcontests.data.entities.Problem
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_dialog_contest.*
import javax.inject.Inject

private const val ARG_CONTEST_URL = "contest_url"

class ContestFragmentDialog : MvpAppCompatDialogFragment(), ContestView {

    @Inject
    @InjectPresenter
    lateinit var presenter: ContestPresenter

    @ProvidePresenter
    fun providePresenter() = presenter
    private var contestUrl: String? = null
    private lateinit var contestProblems: List<Problem>
    private lateinit var contestTitle: String
    private var currentQuestion = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contestUrl = it.getString(ARG_CONTEST_URL)
        }
        setStyle(DialogFragment.STYLE_NORMAL, R.style.QuizStyle)
        retainInstance = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return object : Dialog(context, theme) {
            override fun onBackPressed() {
                if (currentQuestion == 0) {
                    dismiss()
                } else {
                    //presenter.getOneQuestion()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_contest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onAttach(context: Context) {
        ContestApp.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun showError(errorResource: Int) {
        context?.let {
            Toasty.error(it, getString(errorResource), Toast.LENGTH_SHORT).show()
        }
    }

    override fun showContest(title: String, problems: List<Problem>) {
        tv_contest_title.text = title
        contestTitle = title
        contestProblems = problems
        showQuestion()
    }

    override fun showNotConnected() {
        context?.let {
            Toasty.error(it, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showQuestion() {
        val problem = contestProblems[currentQuestion]
        tv_question.text = problem.problem.cmsPage.statement
        val answers = problem.problem.answerChoices
        val type = problem.problem.problemType
    }

    private fun init() {
        iv_back.setOnClickListener {
            //todo presenter.submit last question?
            dismiss()
        }
    }

    companion object {
        val TAG: String = ContestFragmentDialog::class.java.simpleName
        @JvmStatic
        fun newInstance(contestUrl: String) =
                ContestFragmentDialog().apply {
                    arguments = Bundle().apply {
                        putString(ARG_CONTEST_URL, contestUrl)
                    }
                }
    }
}
