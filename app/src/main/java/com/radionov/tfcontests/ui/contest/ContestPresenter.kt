package com.radionov.tfcontests.ui.contest

import com.arellomobile.mvp.InjectViewState
import com.radionov.tfcontests.R
import com.radionov.tfcontests.data.entities.Answer
import com.radionov.tfcontests.data.entities.ContestResponse
import com.radionov.tfcontests.data.entities.Problem
import com.radionov.tfcontests.interactors.ContestInteractor
import com.radionov.tfcontests.ui.common.BasePresenter
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@InjectViewState
class ContestPresenter @Inject constructor(
    private val contestInteractor: ContestInteractor
) : BasePresenter<ContestView>() {

    fun getContest(contestUrl: String) {
        if (isNotConnected()) return
        disposable = Single.zip<ContestResponse, List<Problem>, Pair<ContestResponse, List<Problem>>>(
            contestInteractor.getContestStatus(contestUrl),
            contestInteractor.getContest(contestUrl),
            BiFunction { t1, t2 -> t1 to t2 }
        ).compose(rxComposers.getSingleComposer())
            .subscribe({ (response, problems) ->
                if (problems.isNotEmpty()) {
                    viewState.showContest(response.contest, problems)
                } else {
                    viewState.showError(R.string.error_load_test)
                }
            }, { viewState.showError(R.string.error_load_test) })
    }

    fun startContest(url: String) {
        if (isNotConnected()) return
        disposable = contestInteractor.startContest(url)
            .compose(rxComposers.getCompletableComposer())
            .subscribe(
                { viewState.onTestStart() },
                { viewState.onTestStartFail() }
            )
    }

    fun submitAnswer(url: String, questionId: Int, answer: Answer) {
        if (isNotConnected()) return
        disposable = contestInteractor.submitAnswer(url, questionId, answer)
            .compose(rxComposers.getSingleComposer())
            .subscribe(
                { viewState.onAnswerSubmitted() },
                { viewState.showError(R.string.error_submit_answer) }
            )
    }
}
