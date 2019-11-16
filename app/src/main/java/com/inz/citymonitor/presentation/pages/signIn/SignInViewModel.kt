package com.inz.citymonitor.presentation.pages.signIn

import android.annotation.SuppressLint
import android.database.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.inz.citymonitor.data.model.ErrorResponseModel
import com.inz.citymonitor.data.model.User.SignInUser
import com.inz.citymonitor.data.model.SuccesResponseModel
import com.inz.citymonitor.data.model.User.LocalUser
import com.inz.citymonitor.data.model.User.SignInUserResponse
import com.inz.citymonitor.data.model.User.SignUpUser
import com.inz.citymonitor.data.rest.RetrofitRepository
import com.inz.citymonitor.dependecyInjector.Injector
import com.inz.citymonitor.functional.localStorage.LocalStorage
import io.reactivex.rxkotlin.subscribeBy
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SignInViewModel : ViewModel() {
    @Inject
    lateinit var retrofit: RetrofitRepository

    @Inject
    lateinit var sharedPref:LocalStorage


    var callResult = MutableLiveData<Any>()

    init {
        Injector.componet.inject(this)
    }

    @SuppressLint("CheckResult")
    fun signIn(
        username: String,
        password: String
    ){
        retrofit.signIn(SignInUser(username, password)).subscribeBy(
            onError = {

                callResult.postValue("Bład");
            }
             ,onNext = {
                if(it.isSuccessful) {
                    val restBody = it.body()?.string()
                    val type = object : TypeToken<SignInUserResponse>() {}.type
                    val user = Gson().fromJson(restBody, type) as SignInUserResponse
                    sharedPref.saveUser(user = LocalUser(user.user?.id,user.user?.username,user.user?.firstname,user.user?.surname,user.user?.email,user.user?.isBanned,user.user?.userRating))
                    sharedPref.saveToken(token = user.token)
                    callResult.postValue(user)
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
//if(response.isSuccessful&& response.body()!=null){

//
//    callResult.postValue(o)
//}else{

//}
//}
//
//override fun onFailure(call: Call<JsonElement>, t: Throwable) {
//    callResult.postValue("Bład")
//}

