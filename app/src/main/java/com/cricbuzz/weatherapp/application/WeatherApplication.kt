package com.cricbuzz.weatherapp.application

import android.app.Application
import com.cricbuzz.weatherapp.BuildConfig
import com.cricbuzz.weatherapp.utils.NetworkObserver
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class WeatherApplication: Application() {
    @Inject
    lateinit var networkChangeMonitor: NetworkObserver
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}