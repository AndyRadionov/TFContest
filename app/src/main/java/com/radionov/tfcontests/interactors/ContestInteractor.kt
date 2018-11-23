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
            .map { hwr:HomeWorksResponse -> hwr.homeworks }
            .flatMap { hws -> Observable.fromIterable(hws) }
            .flatMap { hw -> Observable.fromIterable(hw.tasks) }
            .filter { t -> t.task.taskType == "test_during_lecture" }
            .toList()
}