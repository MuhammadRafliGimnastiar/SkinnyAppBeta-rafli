package com.gimnastiar.skinnyappbeta.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gimnastiar.skinnyappbeta.di.Injection
import com.gimnastiar.skinnyappbeta.ui.detect.DetectViewModel
import com.gimnastiar.skinnyappbeta.ui.history.historyData.HistoryDataViewModel
import com.gimnastiar.skinnyappbeta.ui.loginFragment.LoginViewModel
import com.gimnastiar.skinnyappbeta.ui.registerFragment.RegisterViewModel


class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetectViewModel(Injection.provideRepository(context)) as T
        }
        if (modelClass.isAssignableFrom(HistoryDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryDataViewModel(Injection.provideRepository(context)) as T
        }
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(Injection.provideLoginRepo(context)) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(Injection.provideLoginRepo(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}