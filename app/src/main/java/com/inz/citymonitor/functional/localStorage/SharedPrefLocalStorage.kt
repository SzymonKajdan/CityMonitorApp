package com.inz.citymonitor.functional.localStorage

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.inz.citymonitor.data.model.User.LocalUser
import com.inz.citymonitor.data.model.User.SignInUserResponse

class SharedPrefLocalStorage(context: Context) : LocalStorage {
    override fun isLoggedNow(): Boolean {
        var isLogged=sharePref.getBoolean(ISLOGGED, false)
        return isLogged
    }

    override fun logOut() {
        sharePref.edit().apply {
            putString(USER, null)
            saveToken(null)
        }
    }


    override fun saveUser(user: LocalUser) {
        sharePref.edit().apply {
            putString(USER, Gson().toJson(user))
            apply()
        }
    }

    override fun isLogged(): LivePreference<Boolean> {
        return liveSharedPreferences.getBoolean(ISLOGGED, false)
    }

    override fun saveToken(token: String?) {
        sharePref.edit().apply {
            putString(TOKEN, token)
            putBoolean(ISLOGGED, token != null)
            apply()
        }
    }

    override fun getToken(): String? {
        return sharePref.getString(TOKEN, null)
    }

    override fun getUser(): LocalUser? {
        var user = sharePref.getString(USER, null)
        val type = object : TypeToken<LocalUser>() {}.type
        return Gson().fromJson(user, type) as LocalUser

    }

    private var sharePref = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)

    private val liveSharedPreferences = LiveSharedPreferences(sharePref)


    companion object {
        const val PREFSNAME = "CityMonitor"
        const val TOKEN = "token"
        const val ISLOGGED = "isLogged"
        const val USER = "user"
    }
}