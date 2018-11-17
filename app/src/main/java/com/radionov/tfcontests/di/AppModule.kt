package com.radionov.tfcontests.di

import com.radionov.tfcontests.data.datasource.local.Prefs
import com.radionov.tfcontests.data.datasource.remote.FintechApi
import com.radionov.tfcontests.data.repositories.AuthRepository
import com.radionov.tfcontests.interactors.AuthInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Andrey Radionov
 */
@Module
class AppModule {


    @Provides
    @Singleton
    fun provideAuthInteractor(authRepository: AuthRepository): AuthInteractor =
        AuthInteractor(authRepository)

    @Provides
    @Singleton
    fun provideAuthRepository(prefs: Prefs,
                              fintechApi: FintechApi): AuthRepository =
        AuthRepository(prefs, fintechApi)
}
