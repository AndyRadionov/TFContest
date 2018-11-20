package com.radionov.tfcontests.data.datasource.remote

import com.radionov.tfcontests.data.entities.User
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author Andrey Radionov
 */
interface FintechApi {

    @FormUrlEncoded
    @POST("signin")
    fun login(@Field("email") email: String, @Field("password") pass: String): Single<User>

    @FormUrlEncoded
    @POST("lost_password")
    fun restorePass(@Field("email") email: String): Completable

    @POST("signout")
    fun logout(): Completable
}
