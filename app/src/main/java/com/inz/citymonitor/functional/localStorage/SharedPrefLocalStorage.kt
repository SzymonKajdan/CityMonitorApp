package com.inz.citymonitor.functional.localStorage

import android.content.Context

class SharedPrefLocalStorage(context:Context):LocalStorage {
    override fun isLogged(): LivePreference<Boolean> {
            return  liveSharedPreferences.getBoolean(ISLOGGED,false)
    }

    override fun saveToken(token: String?) {
        sharePref.edit().apply {
            putString(TOKEN,token)
            putBoolean(ISLOGGED,token!=null)
            apply()
        }
     }

    override fun getToken(): String? {
       return  sharePref.getString(TOKEN,null)
    }

    private var sharePref=context.getSharedPreferences(PREFSNAME,Context.MODE_PRIVATE)

    private val liveSharedPreferences=LiveSharedPreferences(sharePref)


    companion object {
        const val PREFSNAME="CityMonitor"

        const val TOKEN="token"
        const val ISLOGGED="isLogged"
    }
}