package com.inz.citymonitor.presentation.pages.account.changePassword

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.inz.citymonitor.data.model.ErrorResponseModel
import com.inz.citymonitor.data.model.PasswordModel.PasswordChangeModel
import com.inz.citymonitor.data.model.SuccesResponseModel
import com.inz.citymonitor.data.rest.RetrofitRepository
import com.inz.citymonitor.dependecyInjector.Injector
import com.inz.citymonitor.functional.localStorage.LocalStorage
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ChangePasswordViewModel : ViewModel() {
    @Inject
    lateinit var retrofit: RetrofitRepository

    @Inject
    lateinit var localStorage: LocalStorage

    var callResult = MutableLiveData<Any>()

    init {
        Injector.componet.inject(this)
    }

    @SuppressLint("CheckResult")
    fun changePassword(oldPasword: String, newPassword: String) {
        retrofit.changePassword(PasswordChangeModel(localStorage.getUser()?.id, oldPasword, newPassword)).subscribeBy(
            onError = {
                callResult.postValue(ErrorResponseModel("Timeout", null, null));
            }
            , onNext = {
                if (it.isSuccessful) {
                    localStorage.logOut()
                    callResult.postValue(SuccesResponseModel("OK"))
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
