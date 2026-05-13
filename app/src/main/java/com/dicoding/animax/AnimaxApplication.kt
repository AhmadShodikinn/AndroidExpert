package com.dicoding.animax

import android.app.Application
import com.dicoding.animax.di.AppComponent
import com.dicoding.animax.di.DaggerAppComponent

class AnimaxApplication : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory()
            .create(applicationContext)
    }
}