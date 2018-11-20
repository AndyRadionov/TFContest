package com.radionov.tfcontests

import android.app.Application
import com.facebook.stetho.Stetho
import com.radionov.tfcontests.di.AppComponent
import com.radionov.tfcontests.di.AppModule
import com.radionov.tfcontests.di.DaggerAppComponent
import com.radionov.tfcontests.di.PersistenceModule

/**
 * @author Andrey Radionov
 */
class TFContestsApp : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        appComponent = DaggerAppComponent
            .builder()
            .app(this)
            .build()
    }
}