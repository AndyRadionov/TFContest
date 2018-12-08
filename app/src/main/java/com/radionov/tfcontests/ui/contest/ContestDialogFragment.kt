package com.radionov.tfcontests.ui.contest

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.radionov.tfcontests.ContestApp
import com.radionov.tfcontests.R
import com.radionov.tfcontests.data.entities.Answer
import com.radionov.tfcontests.data.entities.Problem
import com.radionov.tfcontests.utils.ANSWERED_QUESTION_STATUS
import com.radionov.tfcontests.utils.ProblemTypes
import com.radionov.tfcontests.utils.stripHtml
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

    private lateinit var contestUrl: String
    private lateinit var contestProblems: List<Problem>
    private var selectedAnswers = ArrayList<String>()
    private lateinit var contestTitle: String
    private lateinit var currentSelection: IntArray
    private var currentQuestion = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_CONTEST_URL)) {
                contestUrl = it.getString(ARG_CONTEST_URL) as String
            } else {
                showError(R.string.error_load_test)
                dismiss()
            }
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
                    currentQuestion--
                    showQuestion()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_contest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        presenter.getContest(contestUrl)
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
        tv_question_title.text = getString(R.string.question_number, currentQuestion + 1, contestProblems.size)
        val problem = contestProblems[currentQuestion]
        val answers = problem.problem.answerChoices
        clearAnswers(answers)
        tv_question.text = stripHtml(problem.problem.cmsPage.statement)
        val type = ProblemTypes.valueOf(problem.problem.problemType.toUpperCase())

        if (type == ProblemTypes.SELECT_ONE) {
            initSingleQuestion(problem, answers)
        } else {
            initMultipleQuestion(problem, answers)
        }
    }

    private fun initSingleQuestion(problem: Problem, answers: List<String>) {
        for ((index, answer) in answers.withIndex()) {
            val radioButton = layoutInflater.inflate(R.layout.item_radio_button, radio_group, false) as RadioButton
            radioButton.id = index
            radioButton.text = answer
            radio_group.addView(radioButton)
        }
        if (selectedAnswers.isNotEmpty() && problem.status == ANSWERED_QUESTION_STATUS) {
            val selectedArr = selectedAnswers[currentQuestion].toCharArray()
            val selectedAnswer = selectedArr.indexOf('1')
            radio_group.check(selectedAnswer)
        }
        radio_group.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId >= 0) {
                currentSelection[checkedId] = 1
            }
        }
    }

    private fun initMultipleQuestion(problem: Problem, answers: List<String>) {
        for ((index, answer) in answers.withIndex()) {
            val checkBox = layoutInflater.inflate(R.layout.item_checkbox, checkbox_group, false) as CheckBox
            checkBox.text = answer
            checkBox.id = index
            checkbox_group.addView(checkBox)

            if (selectedAnswers.isNotEmpty() && problem.status == ANSWERED_QUESTION_STATUS) {
                val selectedArr = selectedAnswers[currentQuestion].toCharArray()
                selectedArr.forEach {
                    if (it == '1') checkBox.isChecked = true
                }
            }
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                currentSelection[buttonView.id] = 1
            }
        }
    }

    private fun init() {
        iv_back.setOnClickListener {
            dialog.onBackPressed()
        }

        btn_next.setOnClickListener {
            if (currentQuestion == contestProblems.size - 1) {
                //submitAnswer()
            } else {
                currentQuestion++
                showQuestion()
            }

            setupButtons(currentQuestion != contestProblems.size - 1)
        }

        btn_finish.setOnClickListener {
            //submitAnswer()
        }
    }

    private fun setupButtons(isFinishVisible: Boolean) {
        val (submitVisibility, nextBtnText) = if (isFinishVisible)
            View.VISIBLE to R.string.next else View.GONE to R.string.finish
        btn_finish.visibility = submitVisibility
        btn_next.text = getString(nextBtnText)
    }

    private fun clearAnswers(answers: List<String>) {
        currentSelection = IntArray(answers.size)
        radio_group.removeAllViews()
        radio_group.setOnClickListener(null)
        radio_group.clearCheck()
        checkbox_group.removeAllViews()
    }

    private fun submitAnswer() {
        val questionId = contestProblems[currentQuestion].id
        val answer = Answer(selectedAnswers.joinToString(""))
        presenter.submitAnswer(contestUrl, questionId, answer)
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
