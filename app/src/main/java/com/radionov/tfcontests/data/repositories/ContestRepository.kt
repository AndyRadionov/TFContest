package com.radionov.tfcontests.data.repositories

import com.radionov.tfcontests.data.datasource.remote.FintechApi

/**
 * @author Andrey Radionov
 */
class ContestRepository(private val fintechApi: FintechApi) {

    fun getHomeWorks() = fintechApi.getHomeWorks()
}