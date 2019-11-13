package com.inz.citymonitor.dependecyInjector

import com.inz.citymonitor.data.rest.RetrofitRepository
import com.inz.citymonitor.dependecyInjector.modules.AppModule
import com.inz.citymonitor.dependecyInjector.modules.RestModule
import com.inz.citymonitor.presentation.activities.MainActivity
import com.inz.citymonitor.presentation.activities.MainViewModel
import com.inz.citymonitor.presentation.pages.signUp.SingUpViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RestModule::class
    ]
)

interface AppComponent {
    //Activities
    fun inject(into: MainActivity)

    fun inject(inti: MainViewModel)

    //retrofit
    fun inject(into:RetrofitRepository)

    fun inject(into: SingUpViewModel)

}