package com.radionov.tfcontests.di

import android.app.Application
import android.arch.persistence.room.Room
import android.preference.PreferenceManager
import com.radionov.tfcontests.data.datasource.local.Prefs
import com.radionov.tfcontests.data.datasource.local.db.AppDatabase
import com.radionov.tfcontests.data.datasource.local.db.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Andrey Radionov
 */
@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun providePrefs(app: Application): Prefs =
        Prefs(PreferenceManager.getDefaultSharedPreferences(app))

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "tf_contest")
            .build()
}
