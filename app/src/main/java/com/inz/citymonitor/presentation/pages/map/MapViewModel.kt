package com.inz.citymonitor.presentation.pages.map

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.inz.citymonitor.data.model.ErrorResponseModel
import com.inz.citymonitor.data.model.reports.City
import com.inz.citymonitor.data.rest.RetrofitRepository
import com.inz.citymonitor.dependecyInjector.Injector
import com.inz.citymonitor.functional.localStorage.LocalStorage
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MapViewModel : ViewModel() {
    @Inject
    lateinit var retrofit: RetrofitRepository

    @Inject
     lateinit var sharedPref: LocalStorage
    var callResult = MutableLiveData<Any>()

    init {
        Injector.componet.inject(this)
    }

    @SuppressLint("CheckResult")
    fun getReports() {
        retrofit.reports(1).subscribeBy(
            onError = {
                callResult.postValue(ErrorResponseModel("Timeout", null, null));
            }
        ,onNext = {
                if(it.isSuccessful){
                    val restBody=it.body()?.string()
                    val type=object :TypeToken<City>() {}.type
                    val city=Gson().fromJson(restBody,type) as City
                    callResult.postValue(city.reports)
                }else{
                    val restBody=it.errorBody()?.string()
                    val type = object : TypeToken<ErrorResponseModel>() {}.type
                    val errorModel = Gson().fromJson(restBody, type) as ErrorResponseModel
                    callResult.postValue(errorModel)
                }

            }
        )
    }
    fun  isLogged():Boolean{
        return  sharedPref.isLoggedNow();
    }
}
