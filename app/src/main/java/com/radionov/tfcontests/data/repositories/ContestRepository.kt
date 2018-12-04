package com.radionov.tfcontests.data.repositories

import com.radionov.tfcontests.data.datasource.remote.FintechApi

/**
 * @author Andrey Radionov
 */
class ContestRepository(private val fintechApi: FintechApi) {

    fun getHomeWorks() = fintechApi.getHomeWorks()

    fun getContestStatus(url: String) = fintechApi.checkTestStatus(url)

    fun getContest(url: String) = fintechApi.getProblemList(url)

    fun startContest(url: String) = fintechApi.startTest(url)

    fun answerQuestion(url: String) = fintechApi.answerQuestion(url)
}