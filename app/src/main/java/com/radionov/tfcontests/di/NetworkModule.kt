package com.radionov.tfcontests.di

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.radionov.tfcontests.BuildConfig
import com.radionov.tfcontests.data.datasource.local.Prefs
import com.radionov.tfcontests.data.datasource.remote.FintechApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*
import javax.inject.Singleton
import kotlin.collections.HashSet

/**
 * @author Andrey Radionov
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideFintechApi(prefs: Prefs): FintechApi =
        Retrofit.Builder()
            .baseUrl(BuildConfig.TF_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(initOkHttpClient(prefs))
            .build()
            .create(FintechApi::class.java)

    private fun initOkHttpClient(prefs: Prefs): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .setCookieStore(prefs)
            .addInterceptor(StethoInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    private fun initSendCookiesInterceptor(prefs: Prefs): Interceptor {
        return Interceptor { chain ->
            val builder = chain.request().newBuilder()

            prefs.getCsrfToken()?.let {
                builder.addHeader("Cookie", prefs.getCookie())
                builder.addHeader("X-CSRFToken", it)
            }

            builder.addHeader("Referer", BuildConfig.TF_URL)

            chain.proceed(builder.build())
        }
    }

    private fun initSaveCookiesInterceptor(prefs: Prefs): Interceptor {
        return Interceptor { chain ->
            val setCookieHeader = "Set-Cookie"
            val originalResponse = chain.proceed(chain.request())

            val cookies = prefs.getCookies() as HashSet<String>
            if (cookies.isEmpty() && originalResponse.headers(setCookieHeader).isNotEmpty()) {

                originalResponse.headers(setCookieHeader).forEach {
                    cookies.add(it)
                }

                prefs.setCookies(cookies)
            }

            originalResponse
        }
    }

    fun OkHttpClient.Builder.setCookieStore(prefs: Prefs): OkHttpClient.Builder {
        return this
            .addInterceptor(initSendCookiesInterceptor(prefs))
            .addInterceptor(initSaveCookiesInterceptor(prefs))
    }
}
