package com.radionov.tfcontests.di

import android.app.Application
import com.radionov.tfcontests.ui.login.LoginActivity
import com.radionov.tfcontests.ui.splash.SplashActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * @author Andrey Radionov
 */
@Singleton
@Component(modules = [AppModule::class, PersistenceModule::class, NetworkModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: Application): AppComponent.Builder

        fun build(): AppComponent
    }

    fun inject(splashActivity: SplashActivity)
    fun inject(loginActivity: LoginActivity)
}