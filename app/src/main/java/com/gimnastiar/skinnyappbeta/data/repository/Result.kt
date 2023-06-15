package com.gimnastiar.skinnyappbeta.data.repository

import com.gimnastiar.skinnyappbeta.data.remote.model.error.ResponseError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

sealed class Result<T>(
    val payload: T? = null,
    val message: String? = null,
    val exception: Exception? = null,
) {
    class Success<T>(val data: T?) : Result<T>(data)
    class Empty<T>(data: T? = null) : Result<T>(data)
    class Error<T>(exception: Exception?, message: String?) :
        Result<T>(message = message, exception = exception)

    class Loading<T>(data: T? = null) : Result<T>(data)
}

suspend fun <T> proceed(coroutine: suspend () -> T): Result<T> {
    return try {
        Result.Success(coroutine.invoke())
    } catch (exception: Exception) {
        when (exception) {
            is HttpException -> {
                val errorMessageResponseType = object : TypeToken<ResponseError>() {}.type
                val error: ResponseError = Gson().fromJson(
                    exception.response()?.errorBody()?.charStream(),
                    errorMessageResponseType
                )
                Result.Error(exception, error.message)
            }
            else -> {
                Result.Error(exception, exception.message)
            }
        }
    }
}