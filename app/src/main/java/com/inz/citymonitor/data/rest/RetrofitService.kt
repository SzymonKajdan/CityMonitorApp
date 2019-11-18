package com.inz.citymonitor.data.rest

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.inz.citymonitor.data.model.PasswordModel.PasswordChangeModel
import com.inz.citymonitor.data.model.User.EditUser
import com.inz.citymonitor.data.model.User.SignInUser
import com.inz.citymonitor.data.model.User.SignInUserResponse
import com.inz.citymonitor.data.model.User.SignUpUser
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {

    @POST("addUser")
    fun signUpUser(@Body user: SignUpUser):Single<Response<Void>>

    @POST("auth")
    fun signInUser(@Body user: SignInUser): Observable<Response<ResponseBody>>

    @POST("editUser")
    fun editUser(@Body user: EditUser):Observable<Response<ResponseBody>>

    @POST("changePassword")
    fun changePassword(@Body password: PasswordChangeModel):Observable<Response<ResponseBody>>
}