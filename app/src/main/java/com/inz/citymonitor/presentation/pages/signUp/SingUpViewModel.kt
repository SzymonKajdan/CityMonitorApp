package com.inz.citymonitor.presentation.pages.signUp

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.inz.citymonitor.data.model.ErrorResponseModel
import com.inz.citymonitor.data.model.SignUpUser
import com.inz.citymonitor.data.model.SuccesResponseModel
import com.inz.citymonitor.data.rest.RetrofitRepository
import com.inz.citymonitor.dependecyInjector.Injector
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class SingUpViewModel : ViewModel() {

    @Inject
    lateinit var retrofit: RetrofitRepository

    var callResult = MutableLiveData<Any>()

    init {
        Injector.componet.inject(this)
    }

    @SuppressLint("CheckResult")
    fun signUpUser(
        username: String,
        password: String,
        email: String,
        firstname: String? = null,
        lastname: String? = null
    ) {
        retrofit.signUp(SignUpUser(username, password, email, firstname, lastname)).subscribeBy(
            onError = {
                callResult.postValue("Bład");
            }
            , onSuccess = {
                if (it.code() in (200..299)) {
                    callResult.postValue(
                        SuccesResponseModel(it.code().toString())
                    )
                }else if( it.code() in (400..499)){
                    val type =object : TypeToken<ErrorResponseModel>(){}.type
                    val response=Gson().fromJson(it.errorBody()?.charStream()?.readText(),type )as ErrorResponseModel
                    callResult.postValue(response)

                }
            }
        )

    }
}
