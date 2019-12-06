package com.inz.citymonitor.presentation.pages.report.reportCreator

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.inz.citymonitor.data.model.ErrorResponseModel
import com.inz.citymonitor.data.model.SuccesResponseModel
import com.inz.citymonitor.data.model.reports.ReportPostResource
import com.inz.citymonitor.data.rest.RetrofitRepository
import com.inz.citymonitor.dependecyInjector.Injector
import com.inz.citymonitor.functional.localStorage.LocalStorage
import io.reactivex.rxkotlin.subscribeBy
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import javax.inject.Inject

class ReportCreatorViewModel : ViewModel() {
    @Inject
    lateinit var retrofit: RetrofitRepository

    @Inject
    lateinit var sharedPref: LocalStorage

    var callResult = MutableLiveData<Any>()


    init {
        Injector.componet.inject(this)
    }

    @SuppressLint("CheckResult")
    fun reportAdd(
        longitude: String?,
        latitude: String?,
        description: String,
        reportType: String,
        photoUrl:String?
    ) {

        retrofit.reportAdd(
            ReportPostResource(
                longitude,
                latitude,
                description,
                reportType,
                null,
                null,
                "Lodz"
            )
        ).subscribeBy(
            onError = {
                callResult.postValue("Błąd")
            },
            onNext = {
                if (it.isSuccessful) {
                    callResult.postValue(SuccesResponseModel("Success"))

                } else {
                    val restBody = it.errorBody()?.string()
                    val type = object : TypeToken<ErrorResponseModel>() {}.type
                    val errorModel = Gson().fromJson(restBody, type) as ErrorResponseModel
                    callResult.postValue(errorModel)
                }

            }
        )
    }

    fun getPhoto(data: Intent?, context: Context?): String? {
        var bitmap=data?.extras?.get("data") as Bitmap
        val wrapper = ContextWrapper(context)

// Initialize a new file instance to save bitmap object
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file,"${UUID.randomUUID()}.jpg")
        try{
            // Compress the bitmap and save in jpg format
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
            stream.flush()
            stream.close()

        }catch (e: IOException){
            e.printStackTrace()
        }
        return file.absolutePath
    }

    fun addPhoto(photoUrl: String?) {


    }


}
