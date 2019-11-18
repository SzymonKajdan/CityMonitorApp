package com.inz.citymonitor.presentation.pages.account.resetPassword

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.inz.citymonitor.data.model.ErrorResponseModel
import com.inz.citymonitor.data.model.SuccesResponseModel
import com.inz.citymonitor.data.rest.RetrofitRepository
import com.inz.citymonitor.dependecyInjector.Injector
import com.inz.citymonitor.functional.localStorage.LocalStorage
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ResetPasswordViewModel : ViewModel() {
    @Inject
    lateinit var retrofit: RetrofitRepository

    @Inject
    lateinit var sharedPref: LocalStorage


    var callResult = MutableLiveData<Any>()

    init {
        Injector.componet.inject(this)
    }

    @SuppressLint("CheckResult")
    fun resetPassword(email: String) {
        retrofit.resetPassword(email).subscribeBy(
            onError = {
                callResult.postValue("Bład");
            }
            , onNext = {
                if (it.isSuccessful) {
                    callResult.postValue(SuccesResponseModel("Jeśli podany adres insiteje zosnaie na niego wysłane nowe hasło "))
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
