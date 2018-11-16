package com.radionov.tfcontests.di

import com.radionov.tfcontests.data.datasource.remote.FintechApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author Andrey Radionov
 */
@Module
class NetworkModule {
    companion object {
        private const val BASE_URL = "https://fintech.tinkoff.ru/api"
    }

    @Provides
    @Singleton
    fun provideFintechApi(): FintechApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(FintechApi::class.java)
}
