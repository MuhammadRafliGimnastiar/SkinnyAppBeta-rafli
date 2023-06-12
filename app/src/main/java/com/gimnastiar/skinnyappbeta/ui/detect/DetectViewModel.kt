package com.gimnastiar.skinnyappbeta.ui.detect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gimnastiar.skinnyappbeta.data.remote.model.ResponsePredict
import com.gimnastiar.skinnyappbeta.data.repository.PredictRepository
import com.gimnastiar.skinnyappbeta.data.repository.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody


class DetectViewModel(private val repository: PredictRepository): ViewModel() {

    private val _predict = MutableLiveData<Resource<ResponsePredict>>()
    val predict: LiveData<Resource<ResponsePredict>> = _predict

    fun setPredictData(
        photo: MultipartBody.Part
    ) = viewModelScope.launch {
//        _predict.postValue(repository.getPredict(photo))
    }

    fun predictLiveResponse(
        photo: MultipartBody.Part
    ) = repository.getPredictLive(photo)

    fun addToHistory(
        username: String,
        file: MultipartBody.Part,
        className: String,
        mClass: String
    ) = repository.addHistory(
        username,
        file,
        className,
        mClass
    )

}
