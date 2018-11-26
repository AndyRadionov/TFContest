package com.radionov.tfcontests.di

import com.radionov.tfcontests.data.datasource.local.Prefs
import com.radionov.tfcontests.data.datasource.local.db.UserDao
import com.radionov.tfcontests.data.datasource.remote.FintechApi
import com.radionov.tfcontests.data.repositories.AuthRepository
import com.radionov.tfcontests.data.repositories.ContestRepository
import com.radionov.tfcontests.data.repositories.UserRepository
import com.radionov.tfcontests.interactors.AuthInteractor
import com.radionov.tfcontests.interactors.ContestInteractor
import com.radionov.tfcontests.interactors.UserInteractor
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
    fun provideAuthInteractor(authRepository: AuthRepository, userRepository: UserRepository): AuthInteractor =
        AuthInteractor(authRepository, userRepository)

    @Provides
    @Singleton
    fun provideAuthRepository(prefs: Prefs, fintechApi: FintechApi): AuthRepository =
        AuthRepository(prefs, fintechApi)

    @Provides
    @Singleton
    fun provideContestInteractor(contestRepository: ContestRepository): ContestInteractor =
        ContestInteractor(contestRepository)

    @Provides
    @Singleton
    fun provideContestRepository(fintechApi: FintechApi): ContestRepository =
        ContestRepository(fintechApi)

    @Provides
    @Singleton
    fun provideUserInteractor(userRepository: UserRepository): UserInteractor =
        UserInteractor(userRepository)

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao, fintechApi: FintechApi): UserRepository =
        UserRepository(userDao, fintechApi)
}
