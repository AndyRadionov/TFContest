package com.radionov.tfcontests.interactors

import com.radionov.tfcontests.data.entities.HomeWorksResponse
import com.radionov.tfcontests.data.repositories.ContestRepository
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
            .filter { task -> task.task.taskType == "test_during_lecture" }
            .toList()

    fun getContestStatus(url: String) = contestRepository.getContestStatus(url)

    fun getContest(url: String) = contestRepository.getContest(url)
}
