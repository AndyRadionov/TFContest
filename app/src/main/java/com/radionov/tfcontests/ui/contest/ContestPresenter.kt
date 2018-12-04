package com.radionov.tfcontests.ui.contest

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.radionov.tfcontests.R
import com.radionov.tfcontests.data.entities.ContestInfo
import com.radionov.tfcontests.data.entities.ContestResponse
import com.radionov.tfcontests.data.entities.Problem
import com.radionov.tfcontests.data.entities.Task
import com.radionov.tfcontests.interactors.ContestInteractor
import com.radionov.tfcontests.utils.RxComposers
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
@InjectViewState
class ContestPresenter @Inject constructor(
    private val contestInteractor: ContestInteractor,
    private val rxComposers: RxComposers
) : MvpPresenter<ContestView>() {

    private var disposable: Disposable? = null

    fun getContest(contestUrl: String) {

        disposable?.dispose()

        disposable = Single.zip<ContestResponse, List<Problem>, Pair<ContestResponse, List<Problem>>>(
            contestInteractor.getContestStatus(contestUrl),
            contestInteractor.getContest(contestUrl),
            BiFunction { t1, t2 -> t1 to t2 }
        ).compose(rxComposers.getSingleComposer())
            .subscribe({ (response, problems) ->
                if (problems.isNotEmpty()) {
                    viewState.showContest(response.contest.title, problems)
                } else {
                    viewState.showError(R.string.error_load_test)
                }
            }, { viewState.showError(R.string.error_load_test) })
    }

    fun startContest() {

    }

    fun getQuestion() {

    }

    fun answerQuestion() {

    }
}
