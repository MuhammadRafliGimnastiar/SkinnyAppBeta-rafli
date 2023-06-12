package com.gimnastiar.skinnyappbeta.ui.detailArticle

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gimnastiar.skinnyappbeta.di.Injection
import com.gimnastiar.skinnyappbeta.ui.favorite.FavoriteViewModel
import com.gimnastiar.skinnyappbeta.ui.history.historyData.HistoryDataViewModel

class FavoriteViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailArticleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailArticleViewModel(Injection.provideLocalRepo(context)) as T
        }
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(Injection.provideLocalRepo(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}