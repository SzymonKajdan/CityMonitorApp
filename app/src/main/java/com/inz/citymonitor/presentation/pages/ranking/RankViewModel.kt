package com.inz.citymonitor.presentation.pages.ranking

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.inz.citymonitor.data.model.ErrorResponseModel
import com.inz.citymonitor.data.model.ranking.RankingModel
import com.inz.citymonitor.data.rest.RetrofitRepository
import com.inz.citymonitor.dependecyInjector.Injector
import com.inz.citymonitor.functional.localStorage.LocalStorage
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class RankViewModel : ViewModel() {
    @Inject
    lateinit var retrofit: RetrofitRepository


    var callResult = MutableLiveData<Any>()

    init {
        Injector.componet.inject(this)
    }

    @SuppressLint("CheckResult")
    fun getRankig() {
        retrofit.getRanking().subscribeBy(
            onError = {
                callResult.postValue(ErrorResponseModel("Timeotu", null, null));
            }
        ,onNext = {
                if(it.isSuccessful){
                    val restBody=it.body()?.string()
                    val type = object : TypeToken<List<RankingModel>>() {}.type
                    val list = Gson().fromJson(restBody, type) as List<RankingModel>
                    callResult.postValue(list)
                }else{
                    val restBody=it.errorBody()?.string()
                    val type = object : TypeToken<ErrorResponseModel>() {}.type
                    val errorModel = Gson().fromJson(restBody, type) as ErrorResponseModel
                    callResult.postValue(errorModel)
                }

            }
        )
    }
}
