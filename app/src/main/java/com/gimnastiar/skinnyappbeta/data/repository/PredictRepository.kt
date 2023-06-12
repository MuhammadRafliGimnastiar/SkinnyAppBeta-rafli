package com.gimnastiar.skinnyappbeta.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.gimnastiar.skinnyappbeta.data.local.FavoriteRoomDatabase
import com.gimnastiar.skinnyappbeta.data.remote.model.HistoryResponse
import com.gimnastiar.skinnyappbeta.data.remote.model.ResponsePredict
import com.gimnastiar.skinnyappbeta.data.remote.model.ResponseUpToDb
import com.gimnastiar.skinnyappbeta.data.remote.network.ApiConfig
import com.gimnastiar.skinnyappbeta.data.remote.network.ApiService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Error
import java.security.interfaces.RSAKey

class PredictRepository(
    private val apiService: ApiService
    ) {

    fun getPredictLive(
        photo: MultipartBody.Part
    ) : LiveData<Resource<ResponsePredict>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.getPredict(photo)
            if (response.error){
                emit(Resource.Error(response.msg))
                Log.i("Predict Test", "errorr ${response.msg}")
            } else {
                emit(Resource.Success(response))
                Log.i("Predict Test", "succsess ${response.msg}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message.toString()))
            Log.i("Predict Test exception", "exception ${e.message}")
        }
    }

    fun getAllHistory(
        username: String
    ) : LiveData<Resource<HistoryResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.getHistory(username)
            if (response.error){
                emit(Resource.Error(response.msg))
                Log.i("Predict Test", "errorr ? ${response.error}")
                Log.i("Predict Test", "errorr ${response.msg}")
            } else {
                emit(Resource.Success(response))
                Log.i("Predict Test", "sukses")
                Log.i("Predict Test", "errorr ${response.msg}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message.toString()))
            Log.i("Predict Test exception", "exception ${e.message}")
        }
    }

    fun addHistory(
        username: String,
        file: MultipartBody.Part,
        className: String,
        mClass: String
    ) : LiveData<Resource<ResponseUpToDb>> = liveData {
        emit(Resource.Loading)
        Log.i("PRINT USERNAME", "$username username")
        try {
            val response = apiService.addHistory(
                username,
                file,
                className,
                mClass
            )
            if (response.error){
                emit(Resource.Error(response.msg))
                Log.i("Predict Test", "errorr ? ${response.error}")
                Log.i("Predict Test", "errorr ${response.msg}")
            } else {
                emit(Resource.Success(response))
                Log.i("Predict Test", "sukses")
                Log.i("Predict Test", "errorr ${response.msg}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message.toString()))
            Log.i("Predict Test exception", "exception ${e.message}")
        }
    }

}

interface Predict {
    suspend fun getPredict(
        photo: MultipartBody.Part
    ) : Resource<ResponsePredict>
}
