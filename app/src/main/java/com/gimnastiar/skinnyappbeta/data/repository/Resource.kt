package com.gimnastiar.skinnyappbeta.data.repository

import com.gimnastiar.skinnyappbeta.data.remote.model.ResponseError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException


sealed class Resource<out R> private constructor() {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val data: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()

}