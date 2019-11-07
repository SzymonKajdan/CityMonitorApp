package com.inz.citymonitor.presentation.activities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inz.citymonitor.presentation.customViews.navigation.MenuItem

class MainViewModel:ViewModel() {
    var isLogged = MutableLiveData<Boolean>()


    val itemList = MutableLiveData<List<MenuItem>>()

    val notLoggedItemList = listOf<MenuItem>(
        MenuItem(name = "Zarejestruj"),
        MenuItem(name = "Zaloguj")
    )

    val looggedItemList = listOf<MenuItem>(
        MenuItem(name = "Profil"),
        MenuItem(name = "Ranking"),
        MenuItem(name = "Historia"),
        MenuItem(name = "Wyloguj")
    )
    init {
        isLogged.postValue(false)
    }

    fun setLogged(){
        isLogged.postValue(true)
    }
}