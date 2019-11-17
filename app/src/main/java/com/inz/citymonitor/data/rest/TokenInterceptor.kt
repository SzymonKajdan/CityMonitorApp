package com.inz.citymonitor.data.rest

import com.inz.citymonitor.dependecyInjector.Injector
import com.inz.citymonitor.functional.localStorage.LocalStorage
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor : Interceptor {
    @Inject
    lateinit var localStorage: LocalStorage

    init {
        Injector.componet.inject(this)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
        if (localStorage.isLoggedNow()) {
            localStorage.getToken()?.let {
                newRequest.addHeader("Authorization", "Bearer  $it")
            }
        }
        return chain.proceed(newRequest.build())
    }
}