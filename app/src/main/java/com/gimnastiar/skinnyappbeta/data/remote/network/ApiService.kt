package com.gimnastiar.skinnyappbeta.data.remote.network

import com.gimnastiar.skinnyappbeta.data.remote.model.HistoryResponse
import com.gimnastiar.skinnyappbeta.data.remote.model.ResponseLogin
import com.gimnastiar.skinnyappbeta.data.remote.model.ResponsePredict
import com.gimnastiar.skinnyappbeta.data.remote.model.ResponseRegister
import com.gimnastiar.skinnyappbeta.data.remote.model.ResponseTest
import com.gimnastiar.skinnyappbeta.data.remote.model.ResponseUpToDb
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @Multipart
    @POST("/predict")
    suspend fun getPredict(
        @Part file: MultipartBody.Part,
        @Part("token") token: String
    ) : ResponsePredict


    @GET("/getHistory/{username}")
    suspend fun getHistory(
        @Path("username") username: String
    ) : HistoryResponse


    @Multipart
    @POST("/uploadToDb")
    suspend fun addHistory(
        @Part("username") username: String,
        @Part file: MultipartBody.Part,
        @Part("class_name") className: String,
        @Part("class") classValue: String
    ) : ResponseUpToDb

    @Multipart
    @POST("/register")
    suspend fun register(
        @Part("name") name: String,
        @Part("username") username: String,
        @Part("password") password: String
    ) : ResponseRegister

    @Multipart
    @POST("/login")
    suspend fun login(
        @Part("username") username: String,
        @Part("password") password: String
    ) : ResponseLogin






}