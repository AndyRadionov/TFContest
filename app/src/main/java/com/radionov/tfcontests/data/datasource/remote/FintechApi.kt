package com.radionov.tfcontests.data.datasource.remote

import com.radionov.tfcontests.data.entities.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.RequestBody
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
    fun logout(): Completable

    @GET("course/android_fall2018/homeworks")
    fun getHomeWorks(): Observable<HomeWorksResponse>

    @POST("contest/{url}/start_contest")
    fun startTest(@Path("url") url: String): Completable

    @POST("contest/{url}/problem/{id}")
    fun answerQuestion(@Path("url") url: String): Completable

    @GET("contest/{url}/status")
    fun checkTestStatus(@Path("url") url: String): Single<ContestResponse>

    @GET("contest/{url}/problems")
    fun getProblemList(@Path("url") url: String): Single<List<Problem>>

    @GET("user")
    fun getUser(): Single<UserWithStatus>

    @PUT("register_user")
    fun updateUser(@Body user: User): Completable
}
