package com.inz.citymonitor.presentation.pages.history.historyDetails

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.inz.citymonitor.data.model.ErrorResponseModel
import com.inz.citymonitor.data.model.History.ReportDetailsModel
import com.inz.citymonitor.data.rest.RetrofitRepository
import com.inz.citymonitor.dependecyInjector.Injector
import com.inz.citymonitor.functional.localStorage.LocalStorage
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class HistoryDetailsViewModel : ViewModel() {
    @Inject
    lateinit var retrofit: RetrofitRepository

    @Inject
     lateinit var sharedPref: LocalStorage


    var callResult = MutableLiveData<Any>()

    init {
        Injector.componet.inject(this)
    }

    @SuppressLint("CheckResult")
    fun getReport(reportId: Long) {
        retrofit.getReportById(reportId).subscribeBy(
            onError = {

                callResult.postValue(ErrorResponseModel("Try again later","",null));
            }
            , onNext = {
                if (it.isSuccessful) {
                    val restBody = it.body()?.string()
                    val type = object : TypeToken<ReportDetailsModel>() {}.type
                    val report = Gson().fromJson(restBody, type) as ReportDetailsModel
                    callResult.postValue(report)
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
