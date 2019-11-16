package com.inz.citymonitor.data.rest

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.inz.citymonitor.data.model.User.SignInUser
import com.inz.citymonitor.data.model.User.SignInUserResponse
import com.inz.citymonitor.data.model.User.SignUpUser
import com.inz.citymonitor.dependecyInjector.Injector
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class RetrofitRepository @Inject constructor(var retrofitService: RetrofitService) {
    init {
        Injector.componet.inject(this)
    }

    fun signUp(user: SignUpUser): Single<Response<Void>> {
        return retrofitService.signUpUser(user).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }

    fun signIn(userToSend: SignInUser):  Observable<Response<ResponseBody>> {
        return retrofitService.signInUser(userToSend).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }
}