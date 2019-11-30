package com.inz.citymonitor.presentation.pages.history

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.inz.citymonitor.data.model.ErrorResponseModel

import com.inz.citymonitor.data.model.History.HistoryModelLight
import com.inz.citymonitor.data.rest.RetrofitRepository
import com.inz.citymonitor.dependecyInjector.Injector
import com.inz.citymonitor.functional.localStorage.LocalStorage
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class HistoryViewModel : ViewModel() {
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
        retrofit.getReports(sharedPref.getUser()?.id).subscribeBy(
            onError = {

                callResult.postValue(ErrorResponseModel("try again later"," ", emptyList()));
            }
            , onNext = {
                if (it.isSuccessful) {
                    val restBody = it.body()?.string()
                    val type = object : TypeToken<List<HistoryModelLight>>() {}.type
                    val historyList = Gson().fromJson(restBody, type) as List<HistoryModelLight>
                    callResult.postValue(if (!historyList.isEmpty()) historyList else emptyList())
                } else {
                    val restBody = it.errorBody()?.string()
                    val type = object : TypeToken<ErrorResponseModel>() {}.type
                    val errorModel = Gson().fromJson(restBody, type) as ErrorResponseModel
                    callResult.postValue(errorModel)
                }
            }
        )
    }

}
