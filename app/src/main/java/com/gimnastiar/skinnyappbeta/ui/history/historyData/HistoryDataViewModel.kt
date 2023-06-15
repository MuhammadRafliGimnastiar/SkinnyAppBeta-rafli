package com.gimnastiar.skinnyappbeta.ui.history.historyData

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gimnastiar.skinnyappbeta.data.remote.model.HistoryResponse
import com.gimnastiar.skinnyappbeta.data.repository.PredictRepository
import com.gimnastiar.skinnyappbeta.data.repository.Result
import kotlinx.coroutines.launch

class HistoryDataViewModel(private val repository: PredictRepository): ViewModel() {

//    private var _data = MutableLiveData<Result<HistoryResponse>>()
//    val data: LiveData<Result<HistoryResponse>> = _data

    fun getHistory(username: String) = repository.getAllHistory(username)

//    fun getHistory(username: String) {
//        viewModelScope.launch {
//            _data.postValue(repository.getAllHistory2(username))
//        }
//    }

}