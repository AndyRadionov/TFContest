package com.radionov.tfcontests.interactors

import com.radionov.tfcontests.data.entities.Answer
import com.radionov.tfcontests.data.entities.HomeWorksResponse
import com.radionov.tfcontests.data.entities.Task
import com.radionov.tfcontests.data.repositories.ContestRepository
import com.radionov.tfcontests.utils.TEST_LECTURE_TYPE
import io.reactivex.Observable
import io.reactivex.Single

/**
 * @author Andrey Radionov
 */
class ContestInteractor(private val contestRepository: ContestRepository) {

    fun getHomeWorks(): Single<MutableList<Task>> =
        contestRepository.getHomeWorks()
            .map { response: HomeWorksResponse -> response.homeworks }
            .flatMapIterable { it }
            .flatMapIterable { it.tasks }
            .filter { task -> task.task.taskType == TEST_LECTURE_TYPE }
            .toList()

    fun getContestStatus(url: String) = contestRepository.getContestStatus(url)

    fun getContest(url: String) = contestRepository.getContest(url)

    fun startContest(url: String) = contestRepository.startContest(url)

    fun submitAnswer(url: String, questionId: Int, answer: Answer) =
        contestRepository.submitAnswer(url, questionId, answer)
}
