package com.inz.citymonitor.data.rest

import com.inz.citymonitor.data.model.PasswordModel.PasswordChangeModel
import com.inz.citymonitor.data.model.User.EditUser
import com.inz.citymonitor.data.model.User.SignInUser
import com.inz.citymonitor.data.model.User.SignUpUser
import com.inz.citymonitor.data.model.reports.FalseNotActiveModel
import com.inz.citymonitor.data.model.reports.MarkModel
import com.inz.citymonitor.data.model.reports.ReportPostResource
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {


    //user
    @POST("signUp")
    fun signUpUser(@Body user: SignUpUser): Single<Response<Void>>

    @POST("logIn")
    fun signInUser(@Body user: SignInUser): Observable<Response<ResponseBody>>

    @POST("editUser")
    fun editUser(@Body user: EditUser): Observable<Response<ResponseBody>>

    @POST("changePassword")
    fun changePassword(@Body password: PasswordChangeModel): Observable<Response<ResponseBody>>

    @POST("resetPassword/{emailAdddres}")
    fun resetPassword(@Path("emailAdddres") email: String): Observable<Response<ResponseBody>>


    //reportHistory
    @GET("reports/getUserReports")
    fun getUserReposrts(@Query("id") id: Long?): Observable<Response<ResponseBody>>

    @GET("reports/getReport/{id}")
    fun getReportById(@Path("id") id: Long?): Observable<Response<ResponseBody>>

    @GET("getRank")
    fun getRanking(): Observable<Response<ResponseBody>>

    @GET(value = "cities/getCityById/{id}")
    fun reports(@Path("id") id: Long?): Observable<Response<ResponseBody>>

    @POST(value = "reports/addReport")
    fun reportAdd(@Body report: ReportPostResource): Observable<Response<ResponseBody>>

    @POST(value = "reports/addMark")
    fun markAdd(@Body report: MarkModel): Observable<Response<ResponseBody>>

    @POST(value = "reports/markAsFalse")
    fun markAsFalse(@Body report: FalseNotActiveModel):Observable<Response<ResponseBody>>

    @POST(value = "reports/markAsNotActive")
    fun markAsNotActive(@Body report: FalseNotActiveModel):Observable<Response<ResponseBody>>
}