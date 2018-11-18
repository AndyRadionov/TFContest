package com.radionov.tfcontests.di

import android.content.Context
import com.radionov.tfcontests.data.datasource.local.Prefs
import com.radionov.tfcontests.data.datasource.remote.FintechApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton

/**
 * @author Andrey Radionov
 */
@Module
class NetworkModule {
    companion object {
        private const val BASE_URL = "https://fintech.tinkoff.ru/api/"
    }

    @Provides
    @Singleton
    fun provideFintechApi(prefs: Prefs): FintechApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(initOkHttpClient(prefs))
            .build()
            .create(FintechApi::class.java)

    private fun initOkHttpClient(prefs: Prefs): OkHttpClient {
        return OkHttpClient.Builder()
            .setCookieStore(prefs)
            .build()
    }

    private fun initSendCookiesInterceptor(prefs: Prefs): Interceptor {
        return Interceptor { chain ->
            val builder = chain.request().newBuilder()
            val cookies = prefs.getCookies()

            cookies?.forEach {
                builder.addHeader("Cookie", it)
                if (it.startsWith("crsf")) {
                    builder.addHeader("X-CSRFToken", it)
                }
            }

            builder.addHeader("Referer", "https://fintech.tinkoff.ru/")

            chain.proceed(builder.build())
        }
    }

    private fun initSaveCookiesInterceptor(prefs: Prefs): Interceptor {
        return Interceptor { chain ->
            val setCookieHeader = "Set-Cookie"
            val originalResponse = chain.proceed(chain.request())

            val cookies = prefs.getCookies() as HashSet<String>
            if (cookies.isEmpty() && !originalResponse.headers(setCookieHeader).isEmpty()) {

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
