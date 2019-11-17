package com.inz.citymonitor.presentation.pages.account.editProfile

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.inz.citymonitor.data.model.ErrorResponseModel
import com.inz.citymonitor.data.model.SuccesResponseModel
import com.inz.citymonitor.data.model.User.EditUser
import com.inz.citymonitor.data.model.User.LocalUser
import com.inz.citymonitor.data.model.User.SignInUserResponse
import com.inz.citymonitor.data.rest.RetrofitRepository
import com.inz.citymonitor.dependecyInjector.Injector
import com.inz.citymonitor.functional.localStorage.LocalStorage
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class EditProfileViewModel : ViewModel() {
    @Inject
    lateinit var retrofit: RetrofitRepository

    @Inject
    lateinit var localStorage: LocalStorage

    var callResult = MutableLiveData<Any>()

    init {
        Injector.componet.inject(this)
    }

    @SuppressLint("CheckResult")
    fun editProfile(
        username: String?,
        email: String?,
        firstname: String? = null,
        lastname: String? = null
    ) {

        retrofit.editUser(userToSend = EditUser(localStorage.getUser()?.id, username, firstname, lastname, email))
            .subscribeBy(
                onError = {
                    callResult.postValue(ErrorResponseModel("Timeotu",null,null));
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
