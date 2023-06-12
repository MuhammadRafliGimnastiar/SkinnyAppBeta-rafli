package com.gimnastiar.skinnyappbeta.ui.history.historyData

import androidx.lifecycle.ViewModel
import com.gimnastiar.skinnyappbeta.data.repository.PredictRepository

class HistoryDataViewModel(private val repository: PredictRepository): ViewModel() {

    fun getHistory(username: String) = repository.getAllHistory(username)
}