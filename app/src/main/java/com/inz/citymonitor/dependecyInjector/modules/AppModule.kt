package com.inz.citymonitor.dependecyInjector.modules

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.inz.citymonitor.app.App
import com.inz.citymonitor.functional.localStorage.LocalStorage
import com.inz.citymonitor.functional.localStorage.SharedPrefLocalStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private  val application: App) {

    @Provides
    @Singleton
    fun provideContext():Context{
        return  application.applicationContext
    }

    @Provides
    @Singleton
    fun provideLocalStorage(): LocalStorage{
        return SharedPrefLocalStorage(application.applicationContext)
    }
}