package com.radionov.tfcontests.di

import android.app.Application
import com.radionov.tfcontests.BuildConfig
import com.radionov.tfcontests.data.datasource.local.Prefs
import com.radionov.tfcontests.data.datasource.remote.FintechApi
import com.radionov.tfcontests.utils.NetworkManager
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author Andrey Radionov
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkManager(app: Application): NetworkManager =
        NetworkManager(app.applicationContext)

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
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }
        return httpClient
            .setCookieStore(prefs)
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

    private fun OkHttpClient.Builder.setCookieStore(prefs: Prefs): OkHttpClient.Builder {
        return this
            .addInterceptor(initSendCookiesInterceptor(prefs))
            .addInterceptor(initSaveCookiesInterceptor(prefs))
    }
}
