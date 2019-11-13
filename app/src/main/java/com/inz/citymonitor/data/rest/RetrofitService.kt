package com.inz.citymonitor.data.rest

import com.inz.citymonitor.data.model.SignUpUser
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {

    @POST("addUser")
    fun signUpUser(@Body user:SignUpUser):Single<Response<Void>>

}