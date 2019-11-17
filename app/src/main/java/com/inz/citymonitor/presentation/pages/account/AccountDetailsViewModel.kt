package com.inz.citymonitor.presentation.pages.account

import androidx.lifecycle.ViewModel;
import com.inz.citymonitor.data.model.User.LocalUser
import com.inz.citymonitor.dependecyInjector.Injector
import com.inz.citymonitor.functional.localStorage.LocalStorage
import javax.inject.Inject

class AccountDetailsViewModel : ViewModel() {
    init {
        Injector.componet.inject(this)
    }
    @Inject
    lateinit var localStorage: LocalStorage


    fun getUser(): LocalUser? {
        return localStorage.getUser()
    }
}
