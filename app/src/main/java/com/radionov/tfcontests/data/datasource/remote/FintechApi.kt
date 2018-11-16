package com.radionov.tfcontests.data.datasource.remote

import retrofit2.http.POST

/**
 * @author Andrey Radionov
 */
interface FintechApi {

    @POST("signin")
    fun login(email: String, pass: String): String
}