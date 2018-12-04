package com.radionov.tfcontests.interactors

import com.radionov.tfcontests.data.entities.Answer
import com.radionov.tfcontests.data.entities.HomeWorksResponse
import com.radionov.tfcontests.data.repositories.ContestRepository
import com.radionov.tfcontests.utils.TEST_LECTURE_TYPE
import io.reactivex.Observable

/**
 * @author Andrey Radionov
 */
class ContestInteractor(private val contestRepository: ContestRepository) {

    fun getHomeWorks() =
        contestRepository.getHomeWorks()
            .map { response:HomeWorksResponse -> response.homeworks }
            .flatMap { homeWorks -> Observable.fromIterable(homeWorks) }
            .flatMap { homeWork -> Observable.fromIterable(homeWork.tasks) }
            .filter { task -> task.task.taskType == TEST_LECTURE_TYPE }
            .toList()

    fun getContestStatus(url: String) = contestRepository.getContestStatus(url)

    fun getContest(url: String) = contestRepository.getContest(url)

    fun startContest(url: String) = contestRepository.startContest(url)

    fun getQuestion(url: String, questionId: Int) = contestRepository.getQuestion(url, questionId)

    fun answerQuestion(url: String, questionId: Int, answer: Answer) =
        contestRepository.answerQuestion(url, questionId, answer)
}
