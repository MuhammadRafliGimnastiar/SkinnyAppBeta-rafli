package com.gimnastiar.skinnyappbeta.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.gimnastiar.skinnyappbeta.data.remote.model.ResponseLogin
import com.gimnastiar.skinnyappbeta.data.remote.model.ResponseRegister
import com.gimnastiar.skinnyappbeta.data.remote.network.ApiService

class LoginRepository(
    private val apiService: ApiService
) {

    fun register(
        name: String,
        username: String,
        password: String
    ) : LiveData<Resource<ResponseRegister>> = liveData {
        Log.i("Info REGIST", "name = $name, username = $username, pass = $password")
        emit(Resource.Loading)
        try {
            val response = apiService.register(
                name,
                username,
                password
            )
            if (response.error){
                emit(Resource.Error(response.message))
                Log.i("Register Test", "errorr ${response.message}")
            } else {
                emit(Resource.Success(response))
                Log.i("Register Test", "succsess ${response.message}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message.toString()))
            Log.i("Register Test exception", "exception ${e.message}")
        }
    }

    fun login(
        username: String,
        password: String
    ) : LiveData<Resource<ResponseLogin>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.login(
                username,
                password
            )
            if (response.error){
                emit(Resource.Error(response.message))
                Log.i("Login Test", "errorr ${response.message}")
            } else {
                emit(Resource.Success(response))
                Log.i("Login Test", "succsess ${response.message}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message.toString()))
            Log.i("Login Test exception", "exception ${e.message}")
        }
    }
}