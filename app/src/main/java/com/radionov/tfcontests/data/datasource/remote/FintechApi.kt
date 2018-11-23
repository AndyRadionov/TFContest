package com.radionov.tfcontests.data.datasource.remote

import com.radionov.tfcontests.data.entities.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

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
    fun logout(@Header("Cookie") cookie: String?): Completable

    @GET("course/android_fall2018/homeworks")
    fun getHomeWorks(): Observable<HomeWorksResponse>

    @POST("contest/lecture_test_{id}/start_contest")
    fun startTest(@Path("id") id: Int): Completable

    @GET("contest/lecture_test_{id}/status")
    fun checkTestStatus(@Path("id") id: Int): Single<ContestResponse>

    @GET("contest/lecture_test_{id}/problems")
    fun getProblemList(@Path("id") id: Int): Single<List<Problem>>
}
