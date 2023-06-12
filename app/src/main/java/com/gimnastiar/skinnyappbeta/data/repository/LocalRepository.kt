package com.gimnastiar.skinnyappbeta.data.repository

import com.gimnastiar.skinnyappbeta.data.local.Favorite
import com.gimnastiar.skinnyappbeta.data.local.FavoriteDao
import com.gimnastiar.skinnyappbeta.data.local.FavoriteRoomDatabase

class LocalRepository(
    private val database: FavoriteRoomDatabase
) {

    private val dao: FavoriteDao

    init {
        dao = database.favoriteDao()
    }

    //add to favorite
    suspend fun addData(
        data: Favorite
    ) = dao.insert(data)

    //get all in favorite
    suspend fun getAllFavorite(
        value: Boolean
    ) = dao.getAllFavorite(value)

    //set favorite true/false
    suspend fun setFavorite(
        value: Boolean,
        title: String
    ) = dao.setFavorite(value, title)

    //get favorite true/false
    suspend fun getData(
        title: String
    ) = dao.getFavoriteUserByUsername(title)


}