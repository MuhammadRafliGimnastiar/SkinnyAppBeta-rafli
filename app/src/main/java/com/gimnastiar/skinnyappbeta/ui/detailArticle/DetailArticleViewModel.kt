package com.gimnastiar.skinnyappbeta.ui.detailArticle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gimnastiar.skinnyappbeta.data.local.Favorite
import com.gimnastiar.skinnyappbeta.data.repository.LocalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.xml.parsers.FactoryConfigurationError

class DetailArticleViewModel(private val repo: LocalRepository): ViewModel() {

    private var _valueFavorite = MutableLiveData<Favorite>()
    val valueFavorite: LiveData<Favorite> = _valueFavorite

    fun addData(
        data: Favorite
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.addData(data)
        }
    }


    fun getFavorite(
        title: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            _valueFavorite.postValue(repo.getData(title))
        }
    }

    fun setFavorite(
        value: Boolean,
        title: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.setFavorite(value, title)
        }
    }

    fun getData(title: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _valueFavorite.postValue(repo.getData(title))
        }
    }

}