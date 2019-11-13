package com.inz.citymonitor.dependecyInjector

import com.inz.citymonitor.app.App
import com.inz.citymonitor.dependecyInjector.modules.AppModule
import com.inz.citymonitor.dependecyInjector.modules.RestModule

object Injector {
lateinit var  componet:AppComponent
    fun init(application: App){
        componet=DaggerAppComponent.builder().appModule(
            AppModule(
                application
            )
        ).restModule(RestModule()).build()
    }
}