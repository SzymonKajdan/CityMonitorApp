package com.inz.citymonitor.functional.localStorage

interface LocalStorage {
    fun saveToken(token:String?)

    fun getToken():String?

    fun isLogged():LivePreference<Boolean>
}