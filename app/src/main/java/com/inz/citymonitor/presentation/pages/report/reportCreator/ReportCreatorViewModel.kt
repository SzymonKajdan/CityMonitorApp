package com.inz.citymonitor.presentation.pages.report.reportCreator

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.loader.content.CursorLoader
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
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
    var firebaseStore:FirebaseStorage?=null
    var photoUrlFireStore:Uri?=null
    var videoFireStore:Uri?=null

    var callResult = MutableLiveData<Any>()


    init {
        Injector.componet.inject(this)
    }


    @SuppressLint("CheckResult")
    fun reportAdd(
        longitude: String?,
        latitude: String?,
        description: String,
        reportType: String
    ) {

        var video:String?=null
        var photo:String?=null
        if (photoUrlFireStore!=null) {
            photo=photoUrlFireStore.toString()
        }

        if(videoFireStore!=null){
                video=videoFireStore.toString()

        }
        retrofit.reportAdd(
            ReportPostResource(
                longitude,
                latitude,
                description,
                reportType,
                photo,
                video,
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
        var bitmap = data?.extras?.get("data") as Bitmap
        val wrapper = ContextWrapper(context)

// Initialize a new file instance to save bitmap object
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")
        try {
            // Compress the bitmap and save in jpg format
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file.absolutePath
    }

    fun addPhoto(photoUrl: String?){
         firebaseStore = FirebaseStorage.getInstance()
        val storageRef = firebaseStore?.reference
        var downloadUri: Uri? = null

        var file = Uri.fromFile(File(photoUrl))
        val riversRef = storageRef?.child("uploads/${file.lastPathSegment}")
        var metadata = StorageMetadata.Builder()
            .setContentType("image/jpg")
            .build()
        var uploadTask = riversRef?.putFile(file, metadata)

        val urlTask = uploadTask?.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            riversRef?.downloadUrl
        }?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG,task.result.toString())
                downloadUri = task.result
                photoUrlFireStore=downloadUri
                callResult.postValue(true)

            } else {
                Log.d(TAG,"fail")
            }

        }

    }

    fun addVideo(videoUri: String?) {
        firebaseStore = FirebaseStorage.getInstance()
        val storageRef = firebaseStore?.reference
        var downloadUri: Uri? = null

        var file = Uri.fromFile(File(videoUri))
        val riversRef = storageRef?.child("uploads/${file.lastPathSegment}")

        var uploadTask = riversRef?.putFile(file)

        val urlTask = uploadTask?.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            riversRef?.downloadUrl
        }?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG,task.result.toString())
                downloadUri = task.result
                videoFireStore=downloadUri
                callResult.postValue(true)

            } else {
                Log.d(TAG,"fail")
            }

        }

    }
    fun path(v: Uri?,context: Context?): String? {
        val cursorLoader = CursorLoader(context!!, v!!, null, null, null, null)
        val cursor = cursorLoader.loadInBackground()
        val idx = cursor?.getColumnIndex(MediaStore.Files.FileColumns.DATA)
        cursor?.moveToFirst()
        var realPath = cursor?.getString(idx!!)
        Log.i("debinf ProdAct", "Real file path in physical device $realPath")
        cursor?.close()

        return realPath
    }

}
