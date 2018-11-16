package com.radionov.tfcontests.di

import android.app.Application
import android.preference.PreferenceManager
import com.radionov.tfcontests.data.datasource.local.Prefs
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
}
