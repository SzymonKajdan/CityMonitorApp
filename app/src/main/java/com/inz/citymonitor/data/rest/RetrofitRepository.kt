package com.inz.citymonitor.data.rest

import com.inz.citymonitor.data.model.PasswordModel.PasswordChangeModel
import com.inz.citymonitor.data.model.User.EditUser
import com.inz.citymonitor.data.model.User.SignInUser
import com.inz.citymonitor.data.model.User.SignUpUser
import com.inz.citymonitor.dependecyInjector.Injector
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class RetrofitRepository @Inject constructor(var retrofitService: RetrofitService) {
    init {
        Injector.componet.inject(this)
    }

    fun signUp(user: SignUpUser): Single<Response<Void>> {
        return retrofitService.signUpUser(user).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun signIn(userToSend: SignInUser): Observable<Response<ResponseBody>> {
        return retrofitService.signInUser(userToSend).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun editUser(userToSend: EditUser): Observable<Response<ResponseBody>> {
        return retrofitService.editUser(userToSend).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun changePassword(passowrd: PasswordChangeModel): Observable<Response<ResponseBody>> {
        return retrofitService.changePassword(passowrd).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun resetPassword(email: String): Observable<Response<ResponseBody>> {
        return retrofitService.resetPassword(email).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun getReports(id: Long?): Observable<Response<ResponseBody>> {
        return retrofitService.getUserReposrts(id).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun getReportById(id: Long?): Observable<Response<ResponseBody>> {
        return retrofitService.getReportById(id).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun getRanking(): Observable<Response<ResponseBody>> {
        return retrofitService.getRanking().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}