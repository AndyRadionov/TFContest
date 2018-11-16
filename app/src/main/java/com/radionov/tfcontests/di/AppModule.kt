package com.radionov.tfcontests.di

import android.app.Application
import com.radionov.tfcontests.data.datasource.local.Prefs
import com.radionov.tfcontests.data.datasource.remote.FintechApi
import com.radionov.tfcontests.data.repositories.AuthRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Andrey Radionov
 */
@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideAuthRepository(prefs: Prefs,
                              fintechApi: FintechApi): AuthRepository =
        AuthRepository(prefs, fintechApi)


}
