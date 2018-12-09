package com.radionov.tfcontests.ui.contest

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.radionov.tfcontests.ContestApp
import com.radionov.tfcontests.R
import com.radionov.tfcontests.data.entities.Answer
import com.radionov.tfcontests.data.entities.Contest
import com.radionov.tfcontests.data.entities.Problem
import com.radionov.tfcontests.utils.ANSWERED_QUESTION_STATUS
import com.radionov.tfcontests.utils.EMPTY_STRING
import com.radionov.tfcontests.utils.ProblemTypes
import com.radionov.tfcontests.utils.stripHtml
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.dialog_test_completed.*
import kotlinx.android.synthetic.main.fragment_dialog_contest.*
import javax.inject.Inject


private const val ARG_CONTEST_URL = "contest_url"

class ContestFragmentDialog : MvpAppCompatDialogFragment(), ContestView {

    @Inject
    @InjectPresenter
    lateinit var presenter: ContestPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    interface OnDismissListener {
        fun onDismiss()
    }

    private var onDismissListener: OnDismissListener? = null
    private lateinit var contestUrl: String
    private lateinit var contestProblems: List<Problem>
    private lateinit var currentSelection: IntArray
    private lateinit var timer: CountDownTimer
    private var selectedAnswers = ArrayList<String>()
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
                    timer.cancel()
                    dismiss()
                } else {
                    currentQuestion--
                    showQuestion()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

    override fun showContest(contest: Contest, problems: List<Problem>) {
        presenter.startContest(contestUrl)
        tv_contest_title.text = contest.title
        contestProblems = problems
        problems.forEach { selectedAnswers.add(it.lastSubmission?.file ?: EMPTY_STRING) }

        initTimer(contest.timeLeft)
    }

    override fun showNotConnected() {
        context?.let {
            Toasty.error(it, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAnswerSubmitted() {
        if (currentQuestion != contestProblems.size - 1) {
            showProgress(false)
            currentQuestion++
            showQuestion()
        } else {
            showFinishDialog()
            timer.cancel()
        }
    }

    override fun onTestStart() {
        timer.start()
        showProgress(false)
        showQuestion()
    }

    override fun onTestStartFail() {
        showError(R.string.error_cant_start_test)
        dismiss()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        onDismissListener?.onDismiss()
        onDismissListener = null
    }

    fun setOnDismissListener(listener: OnDismissListener) {
        onDismissListener = listener
    }

    private fun showQuestion() {
        setupButtons(currentQuestion != contestProblems.size - 1)
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
        val selectedArr = parseSelectedArray(problem)
        for ((index, answer) in answers.withIndex()) {
            val radioButton = layoutInflater.inflate(R.layout.item_radio_button, radio_group, false) as RadioButton
            radioButton.id = index
            radioButton.text = answer
            radio_group.addView(radioButton)
        }
        radio_group.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId >= 0) {
                currentSelection[checkedId] = 1
            }
        }
        if (selectedArr.isNotEmpty()) {
            val selectedAnswer = selectedArr.indexOf('1')
            radio_group.check(selectedAnswer)
        }
    }

    private fun initMultipleQuestion(problem: Problem, answers: List<String>) {
        val selectedArr = parseSelectedArray(problem)
        for ((index, answer) in answers.withIndex()) {
            val checkBox = layoutInflater.inflate(R.layout.item_checkbox, checkbox_group, false) as CheckBox
            checkBox.text = answer
            checkBox.id = index
            checkbox_group.addView(checkBox)

            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                currentSelection[buttonView.id] = if (isChecked) 1 else 0
            }

            if (selectedArr.isNotEmpty() && selectedArr[index] == '1') {
                checkBox.isChecked = true
            }
        }
    }

    private fun init() {
        presenter.getContest(contestUrl)

        iv_back.setOnClickListener {
            dialog.onBackPressed()
        }

        btn_next.setOnClickListener {
            submitAnswer()
        }

        btn_finish.setOnClickListener {
            submitAnswer()
        }
    }

    private fun setupButtons(isFinishVisible: Boolean) {
        val (submitVisibility, nextBtnText) = if (isFinishVisible)
            View.VISIBLE to R.string.next else View.GONE to R.string.finish
        btn_finish.visibility = submitVisibility
        btn_next.text = getString(nextBtnText)
    }

    private fun showProgress(isShown: Boolean = true) {
        val visibility = if (isShown) View.VISIBLE else View.INVISIBLE
        test_progress.visibility = visibility
        btn_next.isEnabled = !isShown
        btn_finish.isEnabled = !isShown
    }

    private fun clearAnswers(answers: List<String>) {
        currentSelection = IntArray(answers.size)
        radio_group.removeAllViews()
        radio_group.setOnClickListener(null)
        radio_group.clearCheck()
        checkbox_group.removeAllViews()
    }

    private fun submitAnswer() {
        showProgress()
        val answerString = currentSelection.joinToString("")
        selectedAnswers[currentQuestion] = answerString
        val answer = Answer(answerString)
        presenter.submitAnswer(contestUrl, currentQuestion + 1, answer)
    }

    private fun parseSelectedArray(problem: Problem): CharArray {
        return if (selectedAnswers[currentQuestion].isNotEmpty() && problem.status == ANSWERED_QUESTION_STATUS) {
            selectedAnswers[currentQuestion].toCharArray()
        } else {
            CharArray(0)
        }
    }

    private fun initTimer(seconds: Int) {
        timer = object : CountDownTimer(seconds.toLong() * MILLISECONDS, MILLISECONDS) {

            override fun onTick(millisUntilFinished: Long) {
                setTime(millisUntilFinished)
            }

            override fun onFinish() {
                showFinishDialog()
            }
        }
    }

    private fun setTime(milliseconds: Long) {
        var seconds = (milliseconds / MILLISECONDS).toInt()
        val minutes = seconds / SECONDS
        seconds %= SECONDS
        tv_test_time.text = String.format(TIMER_FORMAT, minutes, seconds)
    }

    private fun showFinishDialog() {
        dialog.setContentView(R.layout.dialog_test_completed)
        dialog.btn_done.setOnClickListener { dismiss() }
    }

    companion object {
        val TAG: String = ContestFragmentDialog::class.java.simpleName
        const val TIMER_FORMAT = "%d:%02d"
        const val MILLISECONDS = 1000L
        const val SECONDS = 60

        @JvmStatic
        fun newInstance(contestUrl: String) =
            ContestFragmentDialog().apply {
                arguments = Bundle().apply {
                    putString(ARG_CONTEST_URL, contestUrl)
                }
            }
    }
}
