package com.inz.citymonitor.presentation.pages.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.inz.citymonitor.data.rest.RetrofitRepository
import com.inz.citymonitor.dependecyInjector.Injector
import javax.inject.Inject

class MapViewModel : ViewModel() {
    @Inject
    lateinit var retrofit: RetrofitRepository


    var callResult = MutableLiveData<Any>()

    init {
        Injector.componet.inject(this)
    }
}
