package com.inz.citymonitor.data.rest

import com.inz.citymonitor.data.model.SignUpUser
import com.inz.citymonitor.dependecyInjector.Injector
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class RetrofitRepository @Inject constructor( var retrofitService: RetrofitService) {
    init {
        Injector.componet.inject(this)
    }

    fun signUp(user:SignUpUser): Single<Response<Void>>{
        return  retrofitService.signUpUser(user).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }
}