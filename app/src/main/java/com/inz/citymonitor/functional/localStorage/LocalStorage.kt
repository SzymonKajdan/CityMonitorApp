package com.inz.citymonitor.functional.localStorage

import com.inz.citymonitor.data.model.User.LocalUser

interface LocalStorage {
    fun saveToken(token: String?)

    fun getToken(): String?

    fun isLogged(): LivePreference<Boolean>

    fun logOut()

    fun saveUser(user: LocalUser)

    fun getUser():LocalUser?

    fun isLoggedNow():Boolean
}