package com.gimnastiar.skinnyappbeta.di

import android.content.Context
import android.preference.Preference
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.gimnastiar.skinnyappbeta.data.local.FavoriteRoomDatabase
import com.gimnastiar.skinnyappbeta.data.remote.network.ApiConfig
import com.gimnastiar.skinnyappbeta.data.repository.LocalRepository
import com.gimnastiar.skinnyappbeta.data.repository.LoginRepository
import com.gimnastiar.skinnyappbeta.data.repository.PredictRepository
import javax.sql.DataSource

object Injection {

    fun provideRepository(context: Context) : PredictRepository {
        val service = ApiConfig.getApiService()
        return PredictRepository(service)
    }

    fun provideLocalRepo(context: Context) : LocalRepository {
        val database = FavoriteRoomDatabase.getDatabase(context)
        return LocalRepository(database)
    }

    fun provideLoginRepo(context: Context) : LoginRepository {
        val service = ApiConfig.getApiService()
        return LoginRepository(service)
    }

//    fun getSettingStore(context: Context) : DataStore<Preference> {
//        val context.datastore: DataStore<Preference> by preferencesDataStore(name = "setting")
//
//        return datastore
//    }

}