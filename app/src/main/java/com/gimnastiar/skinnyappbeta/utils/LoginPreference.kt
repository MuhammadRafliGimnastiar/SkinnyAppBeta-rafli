package com.gimnastiar.skinnyappbeta.utils

import android.content.Context
import android.util.Log
import com.gimnastiar.skinnyappbeta.data.remote.model.ResponseLogin

class LoginPreference(context: Context) {

    private val preference = context.getSharedPreferences("login_data", Context.MODE_PRIVATE)

    fun saveLogin(data: ResponseLogin) {
        val editor = preference.edit()
        editor.putString("name", data.yourName)
        editor.putString("username", data.yourUsername)
        editor.putString("token", data.token)
        editor.apply()

    }

    fun removeLogin() {
        val editor = preference.edit().clear()
        editor.apply()
    }

    fun getUserData(): UserData {
        val name = preference.getString("name", null)
        val username = preference.getString("username", null)
        val token = preference.getString("token", null)

        val data = UserData(name, username, token)
        return data
    }

    fun hiddenOnBoarding() {
        val editor = preference.edit()
        editor.putBoolean("onboarding", true)
        editor.apply()
    }

    fun showOnBoarding() {
        val editor = preference.edit()
        editor.putBoolean("onboarding", false)
        editor.apply()
    }

    fun getOnboarding() : Boolean {
        return preference.getBoolean("onboarding", false)
    }

    fun getToken(): String? {
        val token = preference.getString("token", null)
        return token
    }
}