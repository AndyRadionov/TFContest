package com.radionov.tfcontests

import android.app.Application
import com.radionov.tfcontests.di.AppComponent
import com.radionov.tfcontests.di.DaggerAppComponent

/**
 * @author Andrey Radionov
 */
class ContestApp : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .app(this)
            .build()
    }
}