package com.gimnastiar.skinnyappbeta.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gimnastiar.skinnyappbeta.data.local.Favorite
import com.gimnastiar.skinnyappbeta.data.repository.LocalRepository
import com.gimnastiar.skinnyappbeta.data.repository.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: LocalRepository): ViewModel() {

    private var _favoriteData = MutableLiveData<List<Favorite>>()
    val favoriteData: LiveData<List<Favorite>> = _favoriteData

    fun getFavorite() {
        CoroutineScope(Dispatchers.IO).launch {
            _favoriteData.postValue(repository.getAllFavorite(true))
        }
    }

}