package com.gimnastiar.skinnyappbeta.ui.homeFragment

import androidx.lifecycle.ViewModel
import com.gimnastiar.skinnyappbeta.data.local.Favorite
import com.gimnastiar.skinnyappbeta.data.repository.LocalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(val repository: LocalRepository): ViewModel() {

    fun addData(
        data: Favorite
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addData(data)
        }
    }
}