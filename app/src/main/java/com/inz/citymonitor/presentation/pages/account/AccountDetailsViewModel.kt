package com.inz.citymonitor.presentation.pages.account

import androidx.lifecycle.ViewModel;
import com.inz.citymonitor.dependecyInjector.Injector

class AccountDetailsViewModel : ViewModel() {
    init {
        Injector.componet.inject(this)
    }
}
