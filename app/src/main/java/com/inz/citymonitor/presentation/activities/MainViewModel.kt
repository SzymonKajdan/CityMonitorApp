package com.inz.citymonitor.presentation.activities

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inz.citymonitor.R
import com.inz.citymonitor.dependecyInjector.Injector
import com.inz.citymonitor.functional.localStorage.LocalStorage
import com.inz.citymonitor.presentation.customViews.navigation.MenuItem
import javax.inject.Inject

class MainViewModel : ViewModel() {
    var isLogged = MutableLiveData<Boolean>()

    init {
        Injector.componet.inject(this)
    }

    @Inject
    lateinit var localStorage: LocalStorage

    val itemList = MutableLiveData<List<MenuItem>>()

    val notLoggedItemList = listOf<MenuItem>(
        MenuItem(name = "Zarejestruj", destinationId = R.id.singUpFragment),
        MenuItem(name = "Zaloguj", destinationId = R.id.singInFragment)
    )

    val looggedItemList = listOf<MenuItem>(
        MenuItem(name = "Profil", destinationId = R.id.accountDetailsFragment),
        MenuItem(name = "Ranking", destinationId = R.id.rankFragment),
        MenuItem(name = "Historia", destinationId = R.id.historyFragment),
        MenuItem(name = "Wyloguj")
    )
//    init {
//        isLogged.postValue(false)
//    }

    fun setLogged() {
        isLogged.postValue(true)
    }

    fun getItemList(isLogged: Boolean): List<MenuItem> {
        val singUp = MenuItem(name = "Zarejestruj", destinationId = R.id.singUpFragment)
        val siginIn = MenuItem(name = "Zaloguj", destinationId = R.id.singInFragment)
        val profile = MenuItem(name = "Profil", destinationId = R.id.accountDetailsFragment)
        val rank = MenuItem(name = "Ranking", destinationId = R.id.rankFragment)
        val history = MenuItem(name = "Historia", destinationId = R.id.historyFragment)
        val logout = MenuItem(name = "Wyloguj")

        return if (isLogged) {
            listOf(profile, rank, history, logout)
        } else {
            listOf(siginIn, singUp)
        }
    }
}