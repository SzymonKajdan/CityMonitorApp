package com.inz.citymonitor.app

import android.app.Application
import com.inz.citymonitor.dependecyInjector.Injector


class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
    }
}