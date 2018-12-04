package com.radionov.tfcontests.data.repositories

import com.radionov.tfcontests.data.datasource.remote.FintechApi
import com.radionov.tfcontests.data.entities.Answer

/**
 * @author Andrey Radionov
 */
class ContestRepository(private val fintechApi: FintechApi) {

    fun getHomeWorks() = fintechApi.getHomeWorks()

    fun getContestStatus(url: String) = fintechApi.checkTestStatus(url)

    fun getContest(url: String) = fintechApi.getProblemList(url)

    fun startContest(url: String) = fintechApi.startContest(url)

    fun getQuestion(url: String, questionId: Int) = fintechApi.getQuestion(url, questionId)

    fun answerQuestion(url: String, questionId: Int, answer: Answer) =
        fintechApi.answerQuestion(url, questionId, answer)
}